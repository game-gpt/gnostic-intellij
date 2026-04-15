package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Enums 声明 PSI 元素
 * 表示 `enums ItemRarity { ... }` 或 `enum ItemRarity { ... }` 形式的枚举声明
 */
class ValkyrieEnumsElement(node: ASTNode) : ValkyrieElement(node), PsiNameIdentifierOwner {

    /**
     * 获取 Enum 名称
     * 对于 `enums ItemRarity { ... }` 返回 `"ItemRarity"`
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
     * 重命名 Enum
     */
    override fun setName(name: String): com.intellij.psi.PsiElement {
        val identifier = nameIdentifier ?: return this
        val newIdentifier = GnosticElementFactory.createIdentifier(name, project)
            ?: return this
        identifier.psi.replace(newIdentifier)
        return this
    }

    override fun toString(): String = "Enums ${getName() ?: "<anonymous>"}"
}
