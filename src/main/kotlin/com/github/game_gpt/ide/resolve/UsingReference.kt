package com.github.game_gpt.ide.resolve

import com.github.game_gpt.ide.index.GnosticNamespaceIndex
import com.github.game_gpt.ide.index.GnosticSymbolInfo
import com.github.game_gpt.language.elements.ValkyrieNamespaceElement
import com.github.game_gpt.language.elements.ValkyrieUsingElement
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
 * Using 引用实现
 * 用于解析 using 声明中的命名空间路径，使其可以导航到对应的 namespace 声明
 * 例如 `using my_shaders::common;` 中的 my_shaders::common 引用
 *
 * @param element using 声明 PSI 元素
 * @param range 引用在元素内的文本范围
 */
class UsingReference(
    private val element: ValkyrieUsingElement,
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
     * 通过 GnosticNamespaceIndex 查找匹配的命名空间声明
     */
    override fun resolve(): PsiElement? {
        val namespacePath = element.getImportPath() ?: return null
        val project = element.project

        val index = FileBasedIndex.getInstance()
        val scope = GlobalSearchScope.allScope(project)

        val values = index.getValues(GnosticNamespaceIndex.NAME, namespacePath, scope)
        val info = values.firstOrNull() ?: return null

        return navigateToElement(project, info)
    }

    /**
     * 根据符号信息导航到对应的 namespace PSI 元素
     */
    private fun navigateToElement(project: Project, info: GnosticSymbolInfo): PsiElement? {
        val virtualFile = VirtualFileManager.getInstance()
            .findFileByUrl(info.fileUrl)
            ?: return null

        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return null
        val element = psiFile.findElementAt(info.offset) ?: return null

        var current: PsiElement? = element
        while (current != null) {
            if (current is ValkyrieNamespaceElement) {
                return current
            }
            if (current.node?.elementType == ValkyrieTypes.NAMESPACE_DECLARATION) {
                return current
            }
            current = current.parent
        }

        return null
    }

    /**
     * 获取引用的规范文本
     */
    override fun getCanonicalText(): String = element.getImportPath() ?: ""

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
