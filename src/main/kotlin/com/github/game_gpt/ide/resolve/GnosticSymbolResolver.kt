package com.github.game_gpt.ide.resolve

import com.github.game_gpt.ide.index.GnosticSymbolIndex
import com.github.game_gpt.ide.index.GnosticSymbolInfo
import com.github.game_gpt.ide.index.GnosticSymbolKey
import com.github.game_gpt.ide.index.GnosticSymbolType
import com.github.game_gpt.language.elements.ValkyrieUsingElement
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex

/**
 * Gnostic 符号解析器
 * 基于 GnosticSymbolIndex 解析符号引用，支持 using 导入和限定名
 *
 * 解析优先级：
 * 1. 同文件中的符号
 * 2. 当前命名空间中的符号
 * 3. using 导入的符号
 * 4. 全局搜索
 */
class GnosticSymbolResolver(private val project: Project) {

    private val index: FileBasedIndex<GnosticSymbolKey, GnosticSymbolInfo>
        get() = FileBasedIndex.getInstance()

    /**
     * 根据名称和引用上下文解析符号
     *
     * @param name 要解析的符号名称
     * @param contextElement 引用所在的 PSI 元素，用于确定当前命名空间和 using 导入
     * @param qualifierNamespace 限定名前缀，如 "game_backend"（来自 game_backend::Player 形式），null 表示非限定名
     * @return 解析到的符号信息列表
     */
    fun resolve(name: String, contextElement: PsiElement, qualifierNamespace: String? = null): List<GnosticSymbolInfo> {
        val currentFile = contextElement.containingFile
        val currentNamespace = extractNamespace(currentFile)
        val usingImports = collectUsingImports(currentFile)

        if (qualifierNamespace != null) {
            return resolveQualifiedName(name, qualifierNamespace)
        }

        val candidates = mutableListOf<GnosticSymbolInfo>()

        val sameFileSymbols = findInFile(name, currentFile)
        candidates.addAll(sameFileSymbols)
        if (candidates.isNotEmpty()) {
            return candidates
        }

        if (currentNamespace.isNotEmpty()) {
            val namespaceSymbols = findByKey(name, currentNamespace)
            candidates.addAll(namespaceSymbols)
        }
        if (candidates.isNotEmpty()) {
            return candidates
        }

        for (import in usingImports) {
            if (import.isWildcard) {
                val namespaceSymbols = findByKey(name, import.namespace)
                candidates.addAll(namespaceSymbols)
            } else if (import.importedName == name) {
                val specificSymbols = findByKey(name, import.namespace)
                candidates.addAll(specificSymbols)
            }
        }
        if (candidates.isNotEmpty()) {
            return candidates
        }

        return findByNameOnly(name)
    }

    /**
     * 解析限定名引用
     * 如 game_backend::Player 中的 Player，限定命名空间为 game_backend
     */
    private fun resolveQualifiedName(name: String, namespace: String): List<GnosticSymbolInfo> {
        return findByKey(name, namespace)
    }

    /**
     * 从 PSI 文件中提取命名空间声明
     */
    private fun extractNamespace(file: PsiFile): String {
        var node = file.node.firstChildNode
        while (node != null) {
            if (node.elementType == ValkyrieTypes.NAMESPACE_DECLARATION) {
                val parts = mutableListOf<String>()
                var child = node.firstChildNode
                while (child != null) {
                    if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                        parts.add(child.text)
                    }
                    child = child.treeNext
                }
                return parts.joinToString(".")
            }
            node = node.treeNext
        }
        return ""
    }

    /**
     * 收集当前文件的所有 using 导入
     */
    private fun collectUsingImports(file: PsiFile): List<UsingImport> {
        val imports = mutableListOf<UsingImport>()
        var node = file.node.firstChildNode
        while (node != null) {
            if (node.elementType == ValkyrieTypes.USING_DECLARATION) {
                val psi = node.psi
                if (psi is ValkyrieUsingElement) {
                    val path = psi.getImportPath()
                    if (path != null) {
                        val lastDot = path.lastIndexOf('.')
                        if (lastDot > 0) {
                            val ns = path.substring(0, lastDot)
                            val importedName = path.substring(lastDot + 1)
                            imports.add(UsingImport(ns, importedName, importedName == "*"))
                        }
                    }
                }
            }
            node = node.treeNext
        }
        return imports
    }

    /**
     * 在同文件中查找符号
     */
    private fun findInFile(name: String, file: PsiFile): List<GnosticSymbolInfo> {
        val namespace = extractNamespace(file)
        val fileUrl = file.virtualFile?.url ?: return emptyList()
        val results = mutableListOf<GnosticSymbolInfo>()

        var node = file.node.firstChildNode
        while (node != null) {
            val symbolName = findFirstIdentifier(node)
            if (symbolName == name) {
                val type = mapNodeTypeToSymbolType(node.elementType) ?: continue
                results.add(GnosticSymbolInfo(name, namespace, type, fileUrl, node.startOffset, node.textLength))
            }
            node = node.treeNext
        }

        return results
    }

    /**
     * 按名称和命名空间查找符号
     */
    private fun findByKey(name: String, namespace: String): List<GnosticSymbolInfo> {
        val key = GnosticSymbolKey(name, namespace)
        val values = index.getValues(GnosticSymbolIndex.NAME, key, GlobalSearchScope.allScope(project))
        return values.toList()
    }

    /**
     * 仅按名称查找符号（全局搜索）
     */
    private fun findByNameOnly(name: String): List<GnosticSymbolInfo> {
        val results = mutableListOf<GnosticSymbolInfo>()
        index.processAllKeys(GnosticSymbolIndex.NAME, project) { key ->
            if (key.name == name) {
                val values = index.getValues(GnosticSymbolIndex.NAME, key, GlobalSearchScope.allScope(project))
                results.addAll(values)
            }
            true
        }
        return results
    }

    /**
     * 获取所有可用的命名空间名称（用于补全）
     */
    fun getAllNamespaces(): Set<String> {
        val namespaces = mutableSetOf<String>()
        index.processAllKeys(GnosticSymbolIndex.NAME, project) { key ->
            if (key.namespace.isNotEmpty()) {
                namespaces.add(key.namespace)
            }
            true
        }
        return namespaces
    }

    /**
     * 获取指定命名空间下的所有符号（用于通配符 using 补全）
     */
    fun getSymbolsInNamespace(namespace: String): List<GnosticSymbolInfo> {
        val results = mutableListOf<GnosticSymbolInfo>()
        index.processAllKeys(GnosticSymbolIndex.NAME, project) { key ->
            if (key.namespace == namespace) {
                val values = index.getValues(GnosticSymbolIndex.NAME, key, GlobalSearchScope.allScope(project))
                results.addAll(values)
            }
            true
        }
        return results
    }

    /**
     * 获取当前上下文中可用的所有符号（用于类型补全）
     */
    fun getAvailableSymbols(contextElement: PsiElement): List<GnosticSymbolInfo> {
        val currentFile = contextElement.containingFile
        val currentNamespace = extractNamespace(currentFile)
        val usingImports = collectUsingImports(currentFile)
        val results = mutableListOf<GnosticSymbolInfo>()

        var node = currentFile.node.firstChildNode
        while (node != null) {
            val type = mapNodeTypeToSymbolType(node.elementType)
            if (type != null) {
                val name = findFirstIdentifier(node) ?: continue
                results.add(GnosticSymbolInfo(name, currentNamespace, type, currentFile.virtualFile?.url ?: "", node.startOffset, node.textLength))
            }
            node = node.treeNext
        }

        if (currentNamespace.isNotEmpty()) {
            results.addAll(getSymbolsInNamespace(currentNamespace))
        }

        for (import in usingImports) {
            if (import.isWildcard) {
                results.addAll(getSymbolsInNamespace(import.namespace))
            } else {
                results.addAll(findByKey(import.importedName, import.namespace))
            }
        }

        return results.distinctBy { GnosticSymbolKey(it.name, it.namespace) }
    }

    /**
     * 将 AST 节点类型映射为符号类型
     */
    private fun mapNodeTypeToSymbolType(elementType: com.intellij.psi.tree.IElementType): GnosticSymbolType? {
        return when (elementType) {
            ValkyrieTypes.NAMESPACE_DECLARATION -> GnosticSymbolType.NAMESPACE
            ValkyrieTypes.CLASS_DECLARATION -> GnosticSymbolType.CLASS
            ValkyrieTypes.MODEL_DECLARATION -> GnosticSymbolType.MODEL
            ValkyrieTypes.SERVICE_DECLARATION -> GnosticSymbolType.SERVICE
            ValkyrieTypes.MESSAGE_DECLARATION -> GnosticSymbolType.MESSAGE
            ValkyrieTypes.ENUM_DECLARATION,
            ValkyrieTypes.ENUMS_DECLARATION -> GnosticSymbolType.ENUM
            ValkyrieTypes.SHADER_DECLARATION -> GnosticSymbolType.SHADER
            ValkyrieTypes.FUNCTION_DECLARATION,
            ValkyrieTypes.MICRO_DECLARATION,
            ValkyrieTypes.MEZZO_DECLARATION,
            ValkyrieTypes.MACRO_DECLARATION -> GnosticSymbolType.FUNCTION
            ValkyrieTypes.LET_DECLARATION -> GnosticSymbolType.VARIABLE
            ValkyrieTypes.CONST_DECLARATION -> GnosticSymbolType.CONSTANT
            else -> null
        }
    }

    /**
     * 查找 AST 节点中第一个 IDENTIFIER 子节点的文本
     */
    private fun findFirstIdentifier(node: com.intellij.lang.ASTNode): String? {
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                return child.text
            }
            child = child.treeNext
        }
        return null
    }

    /**
     * Using 导入信息
     */
    private data class UsingImport(
        /** 导入的命名空间 */
        val namespace: String,
        /** 导入的符号名称，"*" 表示通配符 */
        val importedName: String,
        /** 是否为通配符导入 */
        val isWildcard: Boolean
    )
}
