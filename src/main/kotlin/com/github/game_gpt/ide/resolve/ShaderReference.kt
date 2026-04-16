package com.github.game_gpt.ide.resolve

import com.github.game_gpt.ide.file.GnosticShaderFile
import com.github.game_gpt.ide.index.GnosticSymbolIndex
import com.github.game_gpt.ide.index.GnosticSymbolInfo
import com.github.game_gpt.ide.index.GnosticSymbolKey
import com.github.game_gpt.ide.index.GnosticSymbolType
import com.github.game_gpt.language.elements.ValkyrieShaderElement
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReference
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex

/**
 * Shader 引用实现
 * 用于解析 fallback 块中的 shader 名称引用，支持同文件和跨文件解析
 * 例如 `fallback { shader: PhongShader }` 中的 PhongShader 引用
 *
 * @param element 引用所在的 PSI 元素
 * @param range 引用在元素内的文本范围
 */
class ShaderReference(
    private val element: PsiElement,
    private val range: TextRange
) : PsiReference {

    /**
     * 获取引用所在的 PSI 元素
     */
    override fun getElement(): PsiElement = element

    /**
     * 获取引用在元素内的文本范围
     */
    override fun getRangeInElement(): TextRange = range

    /**
     * 解析引用目标
     * 优先在同文件内查找，再通过符号索引跨文件查找
     */
    override fun resolve(): PsiElement? {
        val shaderName = element.text ?: return null
        val project = element.project

        val localResult = resolveInFile(shaderName)
        if (localResult != null) return localResult

        return resolveAcrossFiles(shaderName, project)
    }

    /**
     * 在同一文件内查找同名 shader 声明
     */
    private fun resolveInFile(shaderName: String): ValkyrieShaderElement? {
        val file = element.containingFile ?: return null
        for (child in file.children) {
            if (child is ValkyrieShaderElement) {
                if (child.getShaderName() == shaderName && child !== element.parent) {
                    return child
                }
            }
        }
        return null
    }

    /**
     * 通过符号索引跨文件查找 shader 声明
     * 优先查找当前文件命名空间内的 shader，再查找 using 导入的命名空间内的 shader，
     * 最后查找全局（无命名空间）的 shader
     */
    private fun resolveAcrossFiles(shaderName: String, project: Project): PsiElement? {
        val index = FileBasedIndex.getInstance()
        val scope = GlobalSearchScope.allScope(project)

        val containingFile = element.containingFile as? GnosticShaderFile
        val currentNamespace = containingFile?.getNamespace() ?: ""
        val usingPaths = containingFile?.getUsingPaths() ?: emptyList()

        val prioritizedInfo = findInNamespace(index, shaderName, currentNamespace, scope)
            ?: usingPaths.firstNotNullOfOrNull { ns ->
                findInNamespace(index, shaderName, ns, scope)
            }
            ?: findInNamespace(index, shaderName, "", scope)
            ?: findGlobal(index, shaderName, scope, project)

        return prioritizedInfo?.let { navigateToElement(project, it) }
    }

    /**
     * 在指定命名空间中查找 shader
     */
    private fun findInNamespace(
        index: FileBasedIndex,
        name: String,
        namespace: String,
        scope: GlobalSearchScope
    ): GnosticSymbolInfo? {
        val key = GnosticSymbolKey(name, namespace)
        val values = index.getValues(GnosticSymbolIndex.NAME, key, scope)
        @Suppress("UNCHECKED_CAST")
        return (values as? Collection<GnosticSymbolInfo>)?.firstOrNull { it.type == GnosticSymbolType.SHADER }
    }

    /**
     * 全局搜索 shader（遍历所有命名空间）
     */
    private fun findGlobal(
        index: FileBasedIndex,
        name: String,
        scope: GlobalSearchScope,
        project: Project
    ): GnosticSymbolInfo? {
        var result: GnosticSymbolInfo? = null
        index.processAllKeys(GnosticSymbolIndex.NAME, { key: GnosticSymbolKey ->
            if (key.name == name) {
                val values = index.getValues(GnosticSymbolIndex.NAME, key, scope)
                @Suppress("UNCHECKED_CAST")
                val shader = (values as? Collection<GnosticSymbolInfo>)?.firstOrNull { it.type == GnosticSymbolType.SHADER }
                if (shader != null) {
                    result = shader
                    return@processAllKeys false
                }
            }
            true
        }, scope, null)
        return result
    }

    /**
     * 根据符号信息导航到对应的 PSI 元素
     */
    private fun navigateToElement(project: Project, info: GnosticSymbolInfo): PsiElement? {
        val virtualFile = VirtualFileManager.getInstance()
            .findFileByUrl(info.fileUrl)
            ?: return null

        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return null
        val element = psiFile.findElementAt(info.offset) ?: return null

        var current: PsiElement? = element
        while (current != null) {
            if (current is ValkyrieShaderElement && current.getShaderName() == info.name) {
                return current
            }
            if (current.node?.elementType == ValkyrieTypes.SHADER_DECLARATION) {
                return current
            }
            current = current.parent
        }

        return null
    }

    /**
     * 获取引用的规范文本
     */
    override fun getCanonicalText(): String = element.text

    /**
     * 处理元素重命名
     */
    override fun handleElementRename(newElementName: String): PsiElement = element

    /**
     * 绑定引用到新元素
     */
    override fun bindToElement(newElement: PsiElement): PsiElement = element

    /**
     * 判断引用是否指向目标元素
     */
    override fun isReferenceTo(target: PsiElement): Boolean {
        val resolved = resolve()
        return resolved != null && resolved == target
    }

    /**
     * 判断是否为软引用（未解析时不报错）
     */
    override fun isSoft(): Boolean = true
}
