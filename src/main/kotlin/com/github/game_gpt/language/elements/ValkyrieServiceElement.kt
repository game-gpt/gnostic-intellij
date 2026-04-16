package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Service 声明 PSI 元素
 * 表示 `service PlayerService { ... }` 形式的 RPC 服务声明
 */
class ValkyrieServiceElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取 Service 名称
     * 对于 `service PlayerService { ... }` 返回 `"PlayerService"`
     */
    override fun getName(): String? {
        return node.findChildByType(ValkyrieTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Service ${getName() ?: "<anonymous>"}"
}
