package com.github.game_gpt.ide.resolve

import com.github.game_gpt.ide.index.GnosticSymbolIndex
import com.github.game_gpt.ide.index.GnosticSymbolInfo
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReference

/**
 * Gnostic 符号引用实现
 * 支持跨文件引用解析，使 Go to Definition 功能可以导航到符号定义处
 *
 * 支持的引用类型：
 * - 类型引用：model/service/message/enum 名称
 * - 限定名引用：namespace::SymbolName 形式
 */
class GnosticReference(
    private val element: PsiElement,
    /** 引用是否在类型引用上下文中 */
    private val isTypeReference: Boolean = false
) : PsiReference {

    private val resolver: GnosticSymbolResolver by lazy {
        GnosticSymbolResolver(element.project)
    }

    override fun getElement(): PsiElement {
        return element
    }

    override fun getRangeInElement(): TextRange {
        return TextRange.from(0, element.textLength)
    }

    override fun resolve(): PsiElement? {
        val name = element.text ?: return null
        val qualifierNamespace = extractQualifierNamespace()

        val candidates = resolver.resolve(name, element, qualifierNamespace)
        if (candidates.isEmpty()) return null

        val symbolInfo = candidates.first()
        return resolveToPsiElement(symbolInfo)
    }

    override fun getCanonicalText(): @NlsSafe String {
        return element.text
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        return element
    }

    override fun bindToElement(newElement: PsiElement): PsiElement {
        return element
    }

    override fun isReferenceTo(target: PsiElement): Boolean {
        val resolved = resolve() ?: return false
        return resolved == target || resolved.manager.areElementsEquivalent(resolved, target)
    }

    override fun isSoft(): Boolean {
        return false
    }

    /**
     * 提取限定名前缀
     * 对于 game_backend::Player 形式，提取 "game_backend" 作为限定命名空间
     */
    private fun extractQualifierNamespace(): String? {
        val parent = element.parent
        if (parent?.node?.elementType != ValkyrieTypes.TYPE_REFERENCE) return null

        val identifiers = parent.node.getChildren(null)
            .filter { it.elementType == ValkyrieTypes.IDENTIFIER }
            .toList()

        if (identifiers.size < 2) return null

        val currentIndex = identifiers.indexOfFirst { it.text == element.text }
        if (currentIndex <= 0) return null

        val doubleColon = parent.node.getChildren(null)
            .any { it.elementType == ValkyrieTypes.DOUBLE_COLON }

        if (doubleColon) {
            return identifiers[0].text
        }

        val dot = parent.node.getChildren(null)
            .any { it.elementType == ValkyrieTypes.DOT }

        if (dot && identifiers.size >= 2) {
            return identifiers.subList(0, currentIndex).joinToString(".") { it.text }
        }

        return null
    }

    /**
     * 将符号信息解析为 PSI 元素
     */
    private fun resolveToPsiElement(symbolInfo: GnosticSymbolInfo): PsiElement? {
        val virtualFile = VirtualFileManager.getInstance().findFileByUrl(symbolInfo.fileUrl) ?: return null
        val psiFile = PsiManager.getInstance(element.project).findFile(virtualFile) ?: return null

        val leafElement = psiFile.findElementAt(symbolInfo.offset) ?: return null

        var current: PsiElement? = leafElement
        while (current != null) {
            if (current.textOffset == symbolInfo.offset && current.textLength == symbolInfo.length) {
                return current
            }
            if (current.node?.elementType in ValkyrieTypes.DECLARATIONS) {
                return current
            }
            current = current.parent
        }

        return null
    }
}
