package com.github.game_gpt.ide.formatting

import com.intellij.formatting.FormattingDocumentModel
import com.intellij.lang.ASTNode
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange

/**
 * A formatting document model implementation that wraps an IntelliJ Document.
 * This model provides document access methods required for formatting operations.
 *
 * @property document The underlying IntelliJ document being formatted
 */
class GnosticFormattingDocumentModel(private val document: Document) : FormattingDocumentModel {

    /**
     * Returns the line number at the given offset in the document.
     *
     * @param offset The character offset in the document
     * @return The zero-based line number at the specified offset
     */
    override fun getLineNumber(offset: Int): Int {
        return document.getLineNumber(offset)
    }

    /**
     * Returns the start offset of the specified line.
     *
     * @param line The zero-based line number
     * @return The character offset of the line's start position
     */
    override fun getLineStartOffset(line: Int): Int {
        return document.getLineStartOffset(line)
    }

    /**
     * Returns the text within the specified range.
     *
     * @param textRange The range of text to retrieve, or null
     * @return The text sequence within the range, or null if textRange is null
     */
    override fun getText(textRange: TextRange?): CharSequence? {
        if (textRange == null) return null
        return document.getText(textRange)
    }

    /**
     * Returns the total length of the document text.
     *
     * @return The number of characters in the document
     */
    override fun getTextLength(): Int {
        return document.textLength
    }

    /**
     * Returns the underlying document being formatted.
     *
     * @return The IntelliJ Document instance
     */
    override fun getDocument(): Document {
        return document
    }

    /**
     * Checks whether the specified range contains only whitespace characters.
     *
     * @param startOffset The start offset of the range
     * @param endOffset The end offset of the range
     * @return True if the range contains only whitespace, false otherwise
     */
    override fun containsWhiteSpaceSymbolsOnly(startOffset: Int, endOffset: Int): Boolean {
        val text = document.charsSequence.subSequence(startOffset, endOffset)
        return text.all { it.isWhitespace() }
    }

    /**
     * Adjusts whitespace if necessary for formatting.
     * This basic implementation returns the whitespace text unchanged.
     *
     * @param whiteSpaceText The original whitespace text
     * @param startOffset The start offset of the whitespace range
     * @param endOffset The end offset of the whitespace range
     * @param nodeAfter The AST node following the whitespace, or null
     * @param changedViaPsi Whether the change was made via PSI
     * @return The whitespace text unchanged
     */
    override fun adjustWhiteSpaceIfNecessary(
        whiteSpaceText: CharSequence,
        startOffset: Int,
        endOffset: Int,
        nodeAfter: ASTNode?,
        changedViaPsi: Boolean
    ): CharSequence {
        return whiteSpaceText
    }
}