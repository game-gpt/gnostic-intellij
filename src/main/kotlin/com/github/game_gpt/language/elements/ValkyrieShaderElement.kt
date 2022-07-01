package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Gnostic Shader 元素
 * 用于表示 Gnostic 语言中的 shader 语句
 */
class ValkyrieShaderElement(node: ASTNode) : GnosticElement(node) {
    /**
     * 获取 shader 名称
     */
    fun getShaderName(): String? {
        val shaderNameNode = node.findChildByType(ValkyrieTypes.IDENTIFIER)
        return shaderNameNode?.text
    }

    /**
     * 获取 shader 的成员
     */
    fun getMembers(): Array<GnosticElement> {
        return findChildrenByClass(GnosticElement::class.java)
    }

    /**
     * 获取 shader 的文本表示
     */
    override fun getText(): String {
        return "shader ${getShaderName() ?: "Unknown"}"
    }
}
