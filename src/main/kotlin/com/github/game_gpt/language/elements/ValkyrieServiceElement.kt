package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Service 声明 PSI 元素
 * 表示 `service PlayerService { ... }` 形式的 RPC 服务声明
 */
class ValkyrieServiceElement(node: ASTNode) : ValkyrieElement(node), PsiNameIdentifierOwner {

    /**
     * 获取 Service 名称
     * 对于 `service PlayerService { ... }` 返回 `"PlayerService"`
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
     * 重命名 Service
     */
    override fun setName(name: String): com.intellij.psi.PsiElement {
        val identifier = nameIdentifier ?: return this
        val newIdentifier = GnosticElementFactory.createIdentifier(name, project)
            ?: return this
        identifier.replace(newIdentifier)
        return this
    }

    override fun toString(): String = "Service ${getName() ?: "<anonymous>"}"
}
