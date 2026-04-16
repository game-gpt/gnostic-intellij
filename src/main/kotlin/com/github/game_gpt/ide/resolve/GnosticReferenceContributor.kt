package com.github.game_gpt.ide.resolve

import com.github.game_gpt.language.elements.ValkyrieUsingElement
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

/**
 * Gnostic 引用贡献者
 * 为不同上下文中的标识符注册对应的引用提供者，
 * 支持 fallback shader 引用和 using 声明引用的解析
 */
class GnosticReferenceContributor : PsiReferenceContributor() {

    /**
     * 注册引用提供者
     * 为 fallback 块中的标识符注册 ShaderReferenceProvider
     * 为 using 声明元素注册 UsingReferenceProvider
     */
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            psiElement(ValkyrieTypes.IDENTIFIER)
                .inside(psiElement(ValkyrieTypes.FALLBACK_BLOCK)),
            ShaderReferenceProvider()
        )

        registrar.registerReferenceProvider(
            psiElement(ValkyrieUsingElement::class.java),
            UsingReferenceProvider()
        )
    }
}
