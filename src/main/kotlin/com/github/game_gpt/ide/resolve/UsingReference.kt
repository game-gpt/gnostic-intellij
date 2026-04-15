package com.github.game_gpt.ide.resolve

import com.github.game_gpt.ide.index.GnosticNamespaceIndex
import com.github.game_gpt.ide.index.GnosticSymbolInfo
import com.github.game_gpt.language.elements.ValkyrieNamespaceElement
import com.github.game_gpt.language.elements.ValkyrieUsingElement
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
 */
class UsingReference(
    private val element: ValkyrieUsingElement,
    private val range: TextRange
) : PsiReference {

    override fun getElement(): PsiElement = element

    override fun getRangeInElement(): TextRange = range

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
            if (current.node?.elementType == com.github.game_gpt.language.types.ValkyrieTypes.NAMESPACE_DECLARATION) {
                return current
            }
            current = current.parent
        }

        return null
    }

    override fun getCanonicalText(): String = element.getImportPath() ?: ""

    override fun handleElementRename(newElementName: String): PsiElement = element

    override fun bindToElement(newElement: PsiElement): PsiElement = element

    override fun isReferenceTo(target: PsiElement): Boolean {
        val resolved = resolve()
        return resolved != null && resolved == target
    }

    override fun isSoft(): Boolean = true
}
