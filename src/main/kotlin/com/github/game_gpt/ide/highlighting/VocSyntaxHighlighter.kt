package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.lexer.VocLexer
import com.github.game_gpt.language.types.VocTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType

class VocSyntaxHighlighter : SyntaxHighlighter {
    override fun getHighlightingLexer(): Lexer {
        return VocLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            in VocTypes.KEYWORDS -> arrayOf(KEYWORD_KEY)
            in VocTypes.TAG_TOKENS -> arrayOf(TAG_KEY)
            VocTypes.LITERAL_STRING -> arrayOf(STRING_KEY)
            VocTypes.LITERAL_NUMBER -> arrayOf(NUMBER_KEY)
            VocTypes.IDENTIFIER -> arrayOf(IDENTIFIER_KEY)
            in VocTypes.PARENTHESES -> arrayOf(PAREN_KEY)
            VocTypes.COMMENT -> arrayOf(COMMENT_KEY)
            VocTypes.EQUAL -> arrayOf(OPERATOR_KEY)
            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }

    companion object {
        val KEYWORD_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val TAG_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_TAG",
            DefaultLanguageHighlighterColors.MARKUP_TAG
        )
        val STRING_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_STRING",
            DefaultLanguageHighlighterColors.STRING
        )
        val NUMBER_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )
        val IDENTIFIER_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_IDENTIFIER",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )
        val OPERATOR_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        val PAREN_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_PAREN",
            DefaultLanguageHighlighterColors.PARENTHESES
        )
        val COMMENT_KEY = TextAttributesKey.createTextAttributesKey(
            "VOC_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
    }
}
