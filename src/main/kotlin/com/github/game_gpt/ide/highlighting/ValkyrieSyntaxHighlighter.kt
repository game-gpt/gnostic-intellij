package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.github.game_gpt.language.lexer.ValkyrieLexer
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType

class ValkyrieSyntaxHighlighter : SyntaxHighlighter {
    override fun getHighlightingLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportShaderExtension = true, supportSchemaExtension = true))
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            in ValkyrieTypes.KEYWORDS -> arrayOf(KEYWORD_KEY)
            ValkyrieTypes.LITERAL_STRING -> arrayOf(STRING_KEY)
            ValkyrieTypes.LITERAL_NUMBER -> arrayOf(NUMBER_KEY)
            ValkyrieTypes.IDENTIFIER -> arrayOf(IDENTIFIER_KEY)
            in ValkyrieTypes.OPERATORS -> arrayOf(OPERATOR_KEY)
            in ValkyrieTypes.PARENTHESES -> arrayOf(PAREN_KEY)
            ValkyrieTypes.COMMENT -> arrayOf(COMMENT_KEY)
            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }

    companion object {
        val KEYWORD_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val STRING_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_STRING",
            DefaultLanguageHighlighterColors.STRING
        )
        val NUMBER_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )
        val IDENTIFIER_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_IDENTIFIER",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )
        val OPERATOR_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        val PAREN_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_PAREN",
            DefaultLanguageHighlighterColors.PARENTHESES
        )
        val COMMENT_KEY = TextAttributesKey.createTextAttributesKey(
            "VALKYRIE_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
    }
}

