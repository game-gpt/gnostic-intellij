package com.github.game_gpt.ide.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document

class VonFoldingVisitor(document: Document?, descriptors: MutableList<FoldingDescriptor>) : GnosticFoldingVisitor(document, descriptors) {
    override fun getPlaceholderText(node: ASTNode): String {
        return "{...}"
    }

    override fun buildFoldDescriptors(element: com.intellij.psi.PsiElement) {
        // 实现 Von 格式文件的折叠逻辑
    }
}