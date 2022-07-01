package com.github.game_gpt.ide.documentation

import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager

/**
 * Gnostic 文档注释提供者
 * 用于支持文档注释功能
 */
class GnosticDocumentationProvider : DocumentationProvider {

    /**
     * 获取文档内容
     * @param element Psi元素
     * @param originalElement 原始Psi元素
     * @return 文档内容
     */
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        if (element == null) return null

        // 这里实现文档生成逻辑
        // 例如：解析文档注释，生成HTML格式的文档
        return "<html><body><h2>${element.text}</h2><p>Documentation for ${element.text}</p></body></html>"
    }

    /**
     * 获取文档元素
     * @param psiManager Psi管理器
     * @param obj 查找项对象
     * @param element Psi元素
     * @return 文档元素
     */
    override fun getDocumentationElementForLookupItem(
        psiManager: PsiManager,
        obj: Any,
        element: PsiElement?
    ): PsiElement? {
        return element
    }

    /**
     * 获取文档元素
     * @param elementAtOffset 偏移量处的元素
     * @param originalElement 原始元素
     * @return 文档元素
     */
    override fun getDocumentationElementForLink(
        psiManager: PsiManager,
        link: String,
        context: PsiElement?
    ): PsiElement? {
        return context
    }

}
