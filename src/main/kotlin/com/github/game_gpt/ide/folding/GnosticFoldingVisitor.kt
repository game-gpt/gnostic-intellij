package com.github.game_gpt.ide.folding

import com.github.game_gpt.language.visitor.GnosticRecursiveVisitor
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiElement

/**
 * Gnostic 统一折叠访问器接口
 * 用于处理各种 Gnostic 格式文件的代码折叠
 */
open class GnosticFoldingVisitor(private val document: Document?, private val descriptors: MutableList<FoldingDescriptor>) : GnosticRecursiveVisitor() {
    
    /**
     * 获取占位文本
     * @param node AST节点
     * @return 占位文本
     */
    open fun getPlaceholderText(node: ASTNode): String {
        return "{...}"
    }
    
    /**
     * 构建折叠描述符
     * @param element Psi元素
     */
    open fun buildFoldDescriptors(element: PsiElement) {
        element.accept(this)
    }
    
    /**
     * 访问元素
     * @param element Psi元素
     */
    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        
        // 检查元素是否可以折叠
        if (document != null) {
            val text = element.text
            if (text.startsWith("{") && text.endsWith("}") || text.startsWith("[") && text.endsWith("]")) {
                val startOffset = element.textRange.startOffset
                val endOffset = element.textRange.endOffset
                
                // 确保元素有足够的内容可以折叠
                if (endOffset - startOffset > 2) {
                    val descriptor = FoldingDescriptor(element.node, element.textRange)
                    descriptors.add(descriptor)
                }
            }
        }
    }
}
