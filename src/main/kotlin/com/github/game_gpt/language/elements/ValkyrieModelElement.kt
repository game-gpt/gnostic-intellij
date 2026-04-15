package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Model 声明 PSI 元素
 * 表示 `model Player { ... }` 形式的数据模型声明
 */
class ValkyrieModelElement(node: ASTNode) : ValkyrieElement(node), PsiNameIdentifierOwner {

    /**
     * 获取 Model 名称
     * 对于 `model Player { ... }` 返回 `"Player"`
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
     * 重命名 Model
     */
    override fun setName(name: String): com.intellij.psi.PsiElement {
        val identifier = nameIdentifier ?: return this
        val newIdentifier = com.github.game_gpt.language.elements.GnosticElementFactory.createIdentifier(name, project)
            ?: return this
        identifier.replace(newIdentifier)
        return this
    }

    override fun toString(): String = "Model ${getName() ?: "<anonymous>"}"
}
