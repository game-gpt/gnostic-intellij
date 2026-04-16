package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.VonTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * VON 字典 PSI 元素
 * 表示 VON 格式中的字典结构，如 `{ key: value, ... }`
 */
class VonDictElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取字典中的所有键值对
     */
    fun getPairs(): List<VonPairElement> {
        return children.filterIsInstance<VonPairElement>()
    }

    /**
     * 根据键名获取对应的值元素
     */
    fun getValue(key: String): PsiElement? {
        return getPairs().find { it.getKey() == key }?.getValue()
    }

    /**
     * 获取所有键名
     */
    fun getKeys(): List<String> {
        return getPairs().mapNotNull { it.getKey() }
    }

    override fun toString(): String = "VonDict"
}

/**
 * VON 列表 PSI 元素
 * 表示 VON 格式中的列表结构，如 `[ item1, item2, ... ]`
 */
class VonListElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取列表中的所有元素
     */
    fun getItems(): List<PsiElement> {
        return children.toList()
    }

    /**
     * 获取列表长度
     */
    fun size(): Int = children.count()

    override fun toString(): String = "VonList[${size()}]"
}

/**
 * VON 键值对 PSI 元素
 * 表示 VON 字典中的键值对，如 `key: value`
 */
class VonPairElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取键名
     */
    override fun getName(): String? {
        return node.findChildByType(VonTypes.IDENTIFIER)?.text
    }

    /**
     * 获取键标识符 PSI 元素
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(VonTypes.IDENTIFIER)?.psi
    }

    /**
     * 重命名键名
     */
    override fun setName(name: String): PsiElement {
        return this
    }

    /**
     * 获取键名（字符串形式）
     */
    fun getKey(): String? = getName()

    /**
     * 获取值元素
     */
    fun getValue(): PsiElement? {
        var child = node.firstChildNode
        var foundColon = false
        while (child != null) {
            if (child.elementType == VonTypes.COLON) {
                foundColon = true
            } else if (foundColon && child.psi != null) {
                return child.psi
            }
            child = child.treeNext
        }
        return null
    }

    override fun toString(): String = "VonPair(${getKey()})"
}
