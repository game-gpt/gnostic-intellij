package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Message 声明 PSI 元素
 * 表示 `message GetPlayerRequest { ... }` 形式的 RPC 消息声明
 */
class ValkyrieMessageElement(node: ASTNode) : ValkyrieElement(node), PsiNameIdentifierOwner {

    /**
     * 获取 Message 名称
     * 对于 `message GetPlayerRequest { ... }` 返回 `"GetPlayerRequest"`
     */
    override fun getName(): String? {
        return nameIdentifier?.text
    }

    /**
     * 获取名称标识符节点
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(ValkyrieTypes.IDENTIFIER)?.psi
    }

    /**
     * 重命名 Message
     */
    override fun setName(name: String): com.intellij.psi.PsiElement {
        val identifier = nameIdentifier ?: return this
        val newIdentifier = GnosticElementFactory.createIdentifier(name, project)
            ?: return this
        identifier.psi.replace(newIdentifier)
        return this
    }

    override fun toString(): String = "Message ${getName() ?: "<anonymous>"}"
}
