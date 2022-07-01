package com.github.game_gpt.ide.resolve

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class GnosticReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        // 为标识符注册引用提供者
        registrar.registerReferenceProvider(
            psiElement(ValkyrieTypes.IDENTIFIER),
            GnosticReferenceProvider()
        )
    }
}

