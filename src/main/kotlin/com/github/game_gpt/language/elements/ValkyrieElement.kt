package com.github.game_gpt.language.elements

import com.github.game_gpt.language.GnosticScriptLanguage
import com.intellij.lang.ASTNode
import com.intellij.lang.Language

open class ValkyrieElement(node: ASTNode) : GnosticElement(node) {
    override fun getLanguage(): Language {
        return GnosticScriptLanguage
    }

    override fun toString(): String = node.elementType.toString()
}
