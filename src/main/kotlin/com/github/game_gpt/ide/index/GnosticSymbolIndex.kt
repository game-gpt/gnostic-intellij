package com.github.game_gpt.ide.index

import com.github.game_gpt.language.elements.*
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.KeyDescriptor
import gnu.trove.THashMap

/**
 * Gnostic 符号索引
 * 索引项目中所有 .schema 和 .script 文件的符号声明，
 * 支持按名称和命名空间查找符号定义
 */
class GnosticSymbolIndex : FileBasedIndexExtension<GnosticSymbolKey, GnosticSymbolInfo>() {

    companion object {
        /** 索引 ID */
        val NAME: ID<GnosticSymbolKey, GnosticSymbolInfo> = ID.create("GnosticSymbolIndex")

        /** 索引版本号，数据结构变更时需递增 */
        private const val VERSION = 1
    }

    override fun getName(): ID<GnosticSymbolKey, GnosticSymbolInfo> {
        return NAME
    }

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter { file: VirtualFile ->
            file.extension in setOf("schema", "script")
        }
    }

    override fun dependsOnFileContent(): Boolean {
        return true
    }

    override fun getIndexer(): DataIndexer<GnosticSymbolKey, GnosticSymbolInfo, FileContent> {
        return DataIndexer { content: FileContent ->
            val result = THashMap<GnosticSymbolKey, GnosticSymbolInfo>()
            val psiFile = content.psiFile
            val namespace = extractNamespace(psiFile)
            val fileUrl = content.file.url

            var node = psiFile.node.firstChildNode
            while (node != null) {
                val symbolInfo = extractSymbolInfo(node, namespace, fileUrl)
                if (symbolInfo != null) {
                    val key = GnosticSymbolKey(symbolInfo.name, symbolInfo.namespace)
                    result[key] = symbolInfo
                }
                node = node.treeNext
            }

            result
        }
    }

    override fun getKeyDescriptor(): KeyDescriptor<GnosticSymbolKey> {
        return GnosticSymbolKeyDescriptor()
    }

    override fun getValueExternalizer(): DataExternalizer<GnosticSymbolInfo> {
        return GnosticSymbolInfoExternalizer()
    }

    override fun getVersion(): Int {
        return VERSION
    }

    /**
     * 从 PSI 文件中提取命名空间声明
     */
    private fun extractNamespace(psiFile: com.intellij.psi.PsiFile): String {
        var node = psiFile.node.firstChildNode
        while (node != null) {
            if (node.elementType == ValkyrieTypes.NAMESPACE_DECLARATION) {
                return extractIdentifierPath(node)
            }
            node = node.treeNext
        }
        return ""
    }

    /**
     * 从 AST 节点中提取标识符路径
     * 将所有 IDENTIFIER 子节点用 "." 连接
     */
    private fun extractIdentifierPath(node: ASTNode): String {
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

    /**
     * 从 AST 节点提取符号信息
     */
    private fun extractSymbolInfo(node: ASTNode, namespace: String, fileUrl: String): GnosticSymbolInfo? {
        return when (node.elementType) {
            ValkyrieTypes.NAMESPACE_DECLARATION -> {
                val name = extractIdentifierPath(node)
                if (name.isNotEmpty()) {
                    GnosticSymbolInfo(name, "", GnosticSymbolType.NAMESPACE, fileUrl, node.startOffset, node.textLength)
                } else null
            }
            ValkyrieTypes.CLASS_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.CLASS, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.MODEL_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.MODEL, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.SERVICE_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.SERVICE, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.MESSAGE_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.MESSAGE, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.ENUM_DECLARATION,
            ValkyrieTypes.ENUMS_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.ENUM, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.SHADER_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.SHADER, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.FUNCTION_DECLARATION,
            ValkyrieTypes.MICRO_DECLARATION,
            ValkyrieTypes.MEZZO_DECLARATION,
            ValkyrieTypes.MACRO_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.FUNCTION, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.LET_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.VARIABLE, fileUrl, node.startOffset, node.textLength)
            }
            ValkyrieTypes.CONST_DECLARATION -> {
                val name = findFirstIdentifier(node) ?: return null
                GnosticSymbolInfo(name, namespace, GnosticSymbolType.CONSTANT, fileUrl, node.startOffset, node.textLength)
            }
            else -> null
        }
    }

    /**
     * 查找 AST 节点中第一个 IDENTIFIER 子节点的文本
     */
    private fun findFirstIdentifier(node: ASTNode): String? {
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                return child.text
            }
            child = child.treeNext
        }
        return null
    }
}
