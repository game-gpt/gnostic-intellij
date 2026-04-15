package com.github.game_gpt.ide.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware

class GnosticFoldingBuilder : FoldingBuilder, DumbAware {
    override fun buildFoldRegions(node: ASTNode, document: Document): Array<FoldingDescriptor> {
        val descriptors = mutableListOf<FoldingDescriptor>()
        val psiElement = node.psi
        val file = psiElement.containingFile
        
        // 根据文件类型选择对应的访问器
        if (file != null) {
            val fileExtension = file.virtualFile?.extension
            val visitor = createFoldingVisitor(fileExtension, document, descriptors)
            visitor?.let {
                it.buildFoldDescriptors(psiElement)
            }
        } else {
            // 如果无法获取文件，返回空数组
            return emptyArray()
        }
        
        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        val element = node.psi
        val file = element.containingFile
        val fileExtension = file?.virtualFile?.extension
        
        val visitor = createFoldingVisitor(fileExtension, null, mutableListOf())
        return visitor?.getPlaceholderText(node) ?: "{...}"
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return false
    }
    
    /**
     * 根据文件扩展名创建对应的折叠访问器
     * @param fileExtension 文件扩展名
     * @param document 文档
     * @param descriptors 折叠描述符列表
     * @return 折叠访问器
     */
    private fun createFoldingVisitor(fileExtension: String?, document: Document?, descriptors: MutableList<FoldingDescriptor>): GnosticFoldingVisitor? {
        return when (fileExtension) {
            "von", "script", "shader" -> GnosticFoldingVisitor(document, descriptors)
            else -> null
        }
    }
}
