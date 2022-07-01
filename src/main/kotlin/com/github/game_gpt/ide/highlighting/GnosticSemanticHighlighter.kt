package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.types.VonTypes
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil

class GnosticSemanticHighlighter : HighlightVisitor {
    private var myHolder: HighlightInfoHolder? = null

    override fun suitableForFile(file: PsiFile): Boolean {
        return true
    }

    override fun visit(element: PsiElement) {
        val node = element.node
        val elementType = node?.elementType ?: return

        when (elementType) {
            VonTypes.VON_DICT -> visitVonDict(element)
            VonTypes.VON_PAIR -> visitVonPair(element)
        }
    }

    private fun visitVonDict(element: PsiElement) {
        val node = element.node
        val firstChild = node.firstChildNode ?: return
        val braceL = node.findChildByType(VonTypes.BRACE_L) ?: return

        var current: com.intellij.lang.ASTNode? = firstChild
        while (current != null && current != braceL) {
            // Highlight type names (identifiers before opening brace)
            if (current.elementType == VonTypes.IDENTIFIER) {
                highlightElement(current.psi, VonSyntaxHighlighter.IDENTIFIER_KEY)
                // Continue to handle nested elements
            }
            current = current.treeNext
        }
    }

    private fun visitVonPair(element: PsiElement) {
        val node = element.node
        val firstChild = node.firstChildNode ?: return
        
        // Highlight field names (keys in key-value pairs)
        // Keys can be identifiers or keywords
        if (firstChild.elementType in listOf(
            VonTypes.IDENTIFIER,
            VonTypes.KEYWORD_TRUE,
            VonTypes.KEYWORD_FALSE,
            VonTypes.KEYWORD_NULL
        )) {
            highlightElement(firstChild.psi, VonSyntaxHighlighter.IDENTIFIER_KEY)
        }
    }

    private fun highlightElement(element: PsiElement?, attributesKey: TextAttributesKey) {
        if (element == null || myHolder == null) return
        
        val highlightInfo = com.intellij.codeInsight.daemon.impl.HighlightInfo.newHighlightInfo(
            com.intellij.codeInsight.daemon.impl.HighlightInfoType.INFORMATION
        )
            .range(element.textRange)
            .textAttributes(attributesKey)
            .create()
        
        myHolder?.add(highlightInfo)
    }

    override fun analyze(file: PsiFile, whole: Boolean, holder: HighlightInfoHolder, action: Runnable): Boolean {
        myHolder = holder
        PsiTreeUtil.processElements(file) { element ->
            visit(element)
            true
        }
        action.run()
        return true
    }

    override fun clone(): HighlightVisitor {
        return GnosticSemanticHighlighter()
    }
}
