package com.github.game_gpt.ide.resolve

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

/**
 * Shader 引用提供者
 * 为 fallback 块中 shader 字段值的标识符创建 ShaderReference
 * 仅当标识符是 fallback 块中 `shader:` 字段的值时才创建引用
 */
class ShaderReferenceProvider : PsiReferenceProvider() {

    /**
     * 为 fallback 块中 shader 字段值的标识符创建引用
     */
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (!isFallbackShaderReference(element)) {
            return emptyArray()
        }

        val range = TextRange.from(0, element.textLength)
        return arrayOf(ShaderReference(element, range))
    }

    /**
     * 检查标识符是否为 fallback 块中 shader 字段的值
     * PSI 结构: FALLBACK_BLOCK > FIELD_DECLARATION > EXPRESSION > IDENTIFIER
     * 且 FIELD_DECLARATION 的第一个 IDENTIFIER 子节点文本为 "shader"
     */
    private fun isFallbackShaderReference(element: PsiElement): Boolean {
        val parent = element.parent ?: return false
        if (parent.node?.elementType != ValkyrieTypes.EXPRESSION) return false

        val fieldDecl = parent.parent ?: return false
        if (fieldDecl.node?.elementType != ValkyrieTypes.FIELD_DECLARATION) return false

        val fallbackBlock = fieldDecl.parent ?: return false
        if (fallbackBlock.node?.elementType != ValkyrieTypes.FALLBACK_BLOCK) return false

        val firstIdentifier = fieldDecl.node?.findChildByType(ValkyrieTypes.IDENTIFIER)?.text
        return firstIdentifier == "shader"
    }
}
