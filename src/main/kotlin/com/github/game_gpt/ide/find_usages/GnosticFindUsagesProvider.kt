package com.github.game_gpt.ide.find_usages

import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement

/**
 * Gnostic 查找用法提供者
 * 用于支持查找代码中元素的用法
 */
class GnosticFindUsagesProvider : FindUsagesProvider {

    /**
     * 获取元素的显示名称
     * @param element Psi元素
     * @return 显示名称
     */
    override fun getDescriptiveName(element: PsiElement): String {
        return element.text
    }

    /**
     * 获取元素的类型名称
     * @param element Psi元素
     * @return 类型名称
     */
    override fun getType(element: PsiElement): String {
        return "Element"
    }

    /**
     * 获取元素的使用名称
     * @param element Psi元素
     * @return 使用名称
     */
    override fun getNodeText(element: PsiElement, useFullName: Boolean): String {
        return element.text
    }

    /**
     * 检查元素是否可搜索
     * @param element Psi元素
     * @return 是否可搜索
     */
    override fun canFindUsagesFor(element: PsiElement): Boolean {
        return true
    }

    /**
     * 获取帮助ID
     * @return 帮助ID
     */
    override fun getHelpId(element: PsiElement): String? {
        return null
    }

}
