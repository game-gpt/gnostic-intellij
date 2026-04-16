package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * 命名空间声明 PSI 元素
 * 表示 gs 语言中的 namespace 声明，如 `namespace my_shaders::common;`
 */
class ValkyrieNamespaceElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取命名空间名称（使用点分隔符，兼容非 shader 语言）
     * 对于 `namespace my_shaders::common`，返回 "my_shaders.common"
     */
    override fun getName(): String? {
        val parts = collectIdentifierParts()
        return parts.takeIf { it.isNotEmpty() }?.joinToString(".")
    }

    /**
     * 获取完整的命名空间路径（使用双冒号分隔符，符合 shader 语言规范）
     * 对于 `namespace my_shaders::common::lighting`，返回 "my_shaders::common::lighting"
     * 对于 `namespace my_shaders`，返回 "my_shaders"
     */
    fun getNamespacePath(): String? {
        val parts = collectIdentifierParts()
        return parts.takeIf { it.isNotEmpty() }?.joinToString("::")
    }

    /**
     * 收集命名空间声明中所有标识符文本
     */
    private fun collectIdentifierParts(): List<String> {
        val parts = mutableListOf<String>()
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                parts.add(child.text)
            }
            child = child.treeNext
        }
        return parts
    }

    override fun toString(): String = "Namespace ${getNamespacePath() ?: "<anonymous>"}"
}
