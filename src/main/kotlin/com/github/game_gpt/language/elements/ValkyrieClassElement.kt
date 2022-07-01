package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Gnostic Class 元素
 * 用于表示 Gnostic 语言中的 class 语句
 */
class ValkyrieClassElement(node: ASTNode) : GnosticElement(node) {
    /**
     * 获取类名
     */
    fun getClassName(): String? {
        val classNameNode = node.findChildByType(ValkyrieTypes.IDENTIFIER)
        return classNameNode?.text
    }

    /**
     * 获取类的成员
     */
    fun getMembers(): Array<GnosticElement> {
        return findChildrenByClass(GnosticElement::class.java)
    }

    /**
     * 获取类的文本表示
     */
    override fun getText(): String {
        return "class ${getClassName() ?: "Unknown"}"
    }
}
