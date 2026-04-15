package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Using 声明 PSI 元素
 * 表示 `using game_backend.Player;` 或 `using game_backend.*;` 形式的导入声明
 */
class ValkyrieUsingElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取完整的导入路径
     * 对于 `using game_backend.Player;` 返回 `"game_backend.Player"`
     * 对于 `using game_backend.*;` 返回 `"game_backend.*"`
     */
    fun getImportPath(): String? {
        val parts = mutableListOf<String>()
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                parts.add(child.text)
            } else if (child.elementType == ValkyrieTypes.MULTIPLY) {
                parts.add("*")
            }
            child = child.treeNext
        }
        return parts.takeIf { it.isNotEmpty() }?.joinToString(".")
    }

    /**
     * 获取导入的命名空间部分
     * 对于 `using game_backend.Player;` 返回 `"game_backend"`
     * 对于 `using game_backend.*;` 返回 `"game_backend"`
     */
    fun getImportNamespace(): String? {
        val path = getImportPath() ?: return null
        val lastDot = path.lastIndexOf('.')
        return if (lastDot > 0) path.substring(0, lastDot) else ""
    }

    /**
     * 获取导入的符号名称
     * 对于 `using game_backend.Player;` 返回 `"Player"`
     * 对于 `using game_backend.*;` 返回 `"*"`（表示通配符导入）
     */
    fun getImportedName(): String? {
        val path = getImportPath() ?: return null
        val lastDot = path.lastIndexOf('.')
        return if (lastDot >= 0) path.substring(lastDot + 1) else path
    }

    /**
     * 判断是否为通配符导入
     */
    fun isWildcardImport(): Boolean {
        return getImportedName() == "*"
    }

    override fun toString(): String = "Using ${getImportPath() ?: "<empty>"}"
}
