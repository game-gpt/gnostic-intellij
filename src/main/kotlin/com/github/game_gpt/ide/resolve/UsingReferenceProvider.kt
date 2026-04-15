package com.github.game_gpt.ide.resolve

import com.github.game_gpt.language.elements.ValkyrieUsingElement
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

/**
 * Using 引用提供者
 * 为 using 声明元素创建 UsingReference
 * 引用范围覆盖 using 关键字之后的导入路径部分
 */
class UsingReferenceProvider : PsiReferenceProvider() {

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element !is ValkyrieUsingElement) {
            return emptyArray()
        }

        val importPath = element.getImportPath()
        if (importPath.isNullOrEmpty()) {
            return emptyArray()
        }

        val range = calculatePathRange(element)
        return arrayOf(UsingReference(element, range))
    }

    /**
     * 计算 using 声明中导入路径的文本范围
     * 路径从第一个 IDENTIFIER 开始，到最后一个路径标识符结束
     */
    private fun calculatePathRange(element: ValkyrieUsingElement): TextRange {
        val node = element.node
        var startOffset = -1
        var endOffset = 0

        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.LBRACE) break
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                if (startOffset < 0) {
                    startOffset = child.startOffsetInParent
                }
                endOffset = child.startOffsetInParent + child.textLength
            }
            child = child.treeNext
        }

        if (startOffset < 0) {
            startOffset = 0
            endOffset = element.textLength
        }

        return TextRange(startOffset, endOffset)
    }
}
