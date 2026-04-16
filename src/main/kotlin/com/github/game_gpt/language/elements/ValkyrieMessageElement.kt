package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Message 声明 PSI 元素
 * 表示 `message GetPlayerRequest { ... }` 形式的 RPC 消息声明
 */
class ValkyrieMessageElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取 Message 名称
     * 对于 `message GetPlayerRequest { ... }` 返回 `"GetPlayerRequest"`
     */
    override fun getName(): String? {
        return node.findChildByType(ValkyrieTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Message ${getName() ?: "<anonymous>"}"
}
