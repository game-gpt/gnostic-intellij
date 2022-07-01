package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Gnostic Micro 元素
 * 用于表示 Gnostic 语言中的 micro 语句
 */
class ValkyrieMicroElement(node: ASTNode) : GnosticElement(node) {
    /**
     * 获取 micro 名称
     */
    fun getMicroName(): String? {
        val microNameNode = node.findChildByType(ValkyrieTypes.IDENTIFIER)
        return microNameNode?.text
    }

    /**
     * 获取 micro 的参数
     */
    fun getParameters(): Array<GnosticElement> {
        return findChildrenByClass(GnosticElement::class.java)
    }

    /**
     * 获取 micro 的文本表示
     */
    override fun getText(): String {
        return "micro ${getMicroName() ?: "Unknown"}"
    }
}
