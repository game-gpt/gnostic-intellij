package com.github.game_gpt.ide.resolve

import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference

/**
 * Gnostic 引用实现
 * 用于支持快速导航功能
 */
class GnosticReference(private val element: PsiElement) : PsiReference {
    /**
     * 获取引用元素
     * @return 引用元素
     */
    override fun getElement(): PsiElement {
        return element
    }

    /**
     * 获取引用在元素中的文本范围
     * @return 文本范围
     */
    override fun getRangeInElement(): TextRange {
        return TextRange.from(0, element.textLength)
    }

    /**
     * 解析引用
     * @return 解析后的元素
     */
    override fun resolve(): PsiElement? {
        // 这里实现引用解析逻辑
        // 例如：查找符号定义
        return null
    }

    /**
     * 获取引用的规范文本
     * @return 规范文本
     */
    override fun getCanonicalText(): @NlsSafe String {
        return element.text
    }

    /**
     * 处理元素重命名
     * @param newElementName 新元素名称
     * @return 重命名后的元素
     */
    override fun handleElementRename(newElementName: String): PsiElement {
        return element
    }

    /**
     * 绑定到元素
     * @param element 目标元素
     * @return 绑定后的元素
     */
    override fun bindToElement(element: PsiElement): PsiElement {
        return this.element
    }

    /**
     * 检查是否引用到指定元素
     * @param element 目标元素
     * @return 是否引用到
     */
    override fun isReferenceTo(element: PsiElement): Boolean {
        return resolve() == element
    }

    /**
     * 检查是否为软引用
     * @return 是否为软引用
     */
    override fun isSoft(): Boolean {
        return false
    }

}