package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Enums 声明 PSI 元素
 * 表示 `enums ItemRarity { ... }` 或 `enum ItemRarity { ... }` 形式的枚举声明
 */
class ValkyrieEnumsElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取 Enum 名称
     * 对于 `enums ItemRarity { ... }` 返回 `"ItemRarity"`
     */
    override fun getName(): String? {
        return node.findChildByType(ValkyrieTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Enums ${getName() ?: "<anonymous>"}"
}
