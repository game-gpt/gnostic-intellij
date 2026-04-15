package com.github.game_gpt.ide.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

/**
 * Gnostic 引用提供者
 * 为标识符创建 GnosticReference 实例
 */
class GnosticReferenceProvider(
    /** 是否为类型引用上下文 */
    private val isTypeReference: Boolean = false
) : PsiReferenceProvider() {

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        return arrayOf(GnosticReference(element, isTypeReference))
    }
}
