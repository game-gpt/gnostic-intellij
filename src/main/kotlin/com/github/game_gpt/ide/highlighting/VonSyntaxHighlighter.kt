package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.lexer.VonLexer
import com.github.game_gpt.language.types.VonTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class VonSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer {
        return VonLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            in VonTypes.KEYWORDS -> arrayOf(KEYWORD_KEY)
            VonTypes.LITERAL_STRING -> arrayOf(STRING_KEY)
            VonTypes.LITERAL_NUMBER -> arrayOf(NUMBER_KEY)
            VonTypes.IDENTIFIER -> arrayOf(IDENTIFIER_KEY)
            in VonTypes.PARENTHESES -> arrayOf(PAREN_KEY)
            VonTypes.COLON -> arrayOf(COLON_KEY)
            VonTypes.COMMA -> arrayOf(COMMA_KEY)
            VonTypes.COMMENT -> arrayOf(COMMENT_KEY)
            VonTypes.VON_DICT, VonTypes.VON_LIST, VonTypes.VON_PAIR -> arrayOf(STRUCTURE_KEY)
            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }

    companion object {
        val KEYWORD_KEY = createTextAttributesKey(
            "VON_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val STRING_KEY = createTextAttributesKey(
            "VON_STRING",
            DefaultLanguageHighlighterColors.STRING
        )
        val NUMBER_KEY = createTextAttributesKey(
            "VON_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )
        val IDENTIFIER_KEY = createTextAttributesKey(
            "VON_IDENTIFIER",
            DefaultLanguageHighlighterColors.CLASS_NAME
        )
        val PAREN_KEY = createTextAttributesKey(
            "VON_PAREN",
            DefaultLanguageHighlighterColors.BRACES
        )
        val COLON_KEY = createTextAttributesKey(
            "VON_COLON",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        val COMMA_KEY = createTextAttributesKey(
            "VON_COMMA",
            DefaultLanguageHighlighterColors.COMMA
        )
        val COMMENT_KEY = createTextAttributesKey(
            "VON_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        val STRUCTURE_KEY = createTextAttributesKey(
            "VON_STRUCTURE",
            DefaultLanguageHighlighterColors.CLASS_NAME
        )
    }
}
