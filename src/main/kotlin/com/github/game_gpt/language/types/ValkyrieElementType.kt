package com.github.game_gpt.language.types

import com.github.game_gpt.language.GnosticScriptLanguage
import com.intellij.psi.tree.IElementType

class ValkyrieElementType(debugName: String) : IElementType(debugName, GnosticScriptLanguage) {
    override fun toString(): String {
        return "ValkyrieElement.${debugName}"
    }
}
