package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

class ValkyrieNamespaceElement(node: ASTNode) : ValkyrieElement(node) {
    override fun getName(): String? {
        return node.findChildByType(ValkyrieTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Namespace ${getName() ?: "<anonymous>"}"
}
