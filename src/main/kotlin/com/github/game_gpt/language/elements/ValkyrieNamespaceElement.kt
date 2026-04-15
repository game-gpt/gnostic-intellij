package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Namespace 声明 PSI 元素
 * 表示 `namespace game_backend;` 或 `namespace com.example.game;` 形式的命名空间声明
 */
class ValkyrieNamespaceElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取完整的命名空间路径
     * 对于 `namespace com.example.game;` 返回 `"com.example.game"`
     * 对于 `namespace game_backend;` 返回 `"game_backend"`
     */
    override fun getName(): String? {
        val parts = mutableListOf<String>()
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                parts.add(child.text)
            }
            child = child.treeNext
        }
        return parts.takeIf { it.isNotEmpty() }?.joinToString(".")
    }

    /**
     * 获取命名空间路径，与 getName() 等价
     * 提供更语义化的方法名，用于需要明确获取路径的场景
     */
    fun getNamespacePath(): String? = getName()

    override fun toString(): String = "Namespace ${getName() ?: "<anonymous>"}"
}
