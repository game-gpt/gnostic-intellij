package com.github.game_gpt.language.elements

import com.github.game_gpt.language.GnosticLanguage
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.lang.Language

/**
 * Gnostic PSI 元素基类
 * 所有 Gnostic 语言的 PSI 元素都继承自此类
 */
open class GnosticElement(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getLanguage(): Language {
        return GnosticLanguage
    }
}
