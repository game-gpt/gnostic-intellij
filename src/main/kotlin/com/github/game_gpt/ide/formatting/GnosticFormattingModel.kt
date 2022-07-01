package com.github.game_gpt.ide.formatting

import com.intellij.openapi.editor.Document
import com.intellij.formatting.Block
import com.intellij.formatting.FormattingDocumentModel
import com.intellij.formatting.FormattingModel
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange

/**
 * Gnostic 代码格式化模型
 * 用于管理代码格式化过程中的文档模型和块结构
 *
 * @property node AST 节点，包含格式化的语法树信息
 * @property document 文档对象，用于执行文本操作
 */
class GnosticFormattingModel(
    private val node: ASTNode,
    private val document: Document
) : FormattingModel {

    private val rootBlock: GnosticBlock by lazy {
        GnosticBlock(node)
    }

    private val documentModel: GnosticFormattingDocumentModel by lazy {
        GnosticFormattingDocumentModel(document)
    }

    /**
     * Returns the root block of the formatting model.
     *
     * @return The root GnosticBlock instance
     */
    override fun getRootBlock(): Block {
        return rootBlock
    }

    /**
     * Returns the document model used for formatting.
     *
     * @return The GnosticFormattingDocumentModel instance
     */
    override fun getDocumentModel(): FormattingDocumentModel {
        return documentModel
    }

    /**
     * Replaces whitespace in the specified text range with the given whitespace string.
     *
     * @param textRange The range where whitespace should be replaced, or null
     * @param whiteSpace The new whitespace string to insert
     * @return The new text range after replacement, or null if textRange is null
     */
    override fun replaceWhiteSpace(
        textRange: TextRange?,
        whiteSpace: String?
    ): TextRange? {
        if (textRange == null) return null
        document.replaceString(textRange.startOffset, textRange.endOffset, whiteSpace.orEmpty())
        return TextRange(textRange.startOffset, textRange.startOffset + (whiteSpace?.length ?: 0))
    }

    /**
     * Shifts the indent inside the specified range.
     * Basic implementation that returns null.
     *
     * @param node The AST node to shift indent for
     * @param range The text range to shift indent within
     * @param indent The number of spaces to shift
     * @return null as this is a basic implementation
     */
    override fun shiftIndentInsideRange(
        node: ASTNode?,
        range: TextRange?,
        indent: Int
    ): TextRange? {
        return null
    }

    /**
     * Commits any pending changes to the document.
     * Basic implementation that does nothing.
     */
    override fun commitChanges() {
    }
}