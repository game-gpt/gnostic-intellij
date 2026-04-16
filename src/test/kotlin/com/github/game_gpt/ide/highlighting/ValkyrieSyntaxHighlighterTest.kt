package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.testFramework.LightPlatformTestCase
import org.junit.Assert

class ValkyrieSyntaxHighlighterTest : LightPlatformTestCase() {

    private val highlighter = ValkyrieSyntaxHighlighter()

    fun testKeywordTokens() {
        val keywords = ValkyrieTypes.KEYWORDS.types
        Assert.assertTrue("KEYWORDS TokenSet should not be empty", keywords.isNotEmpty())
        for (keyword in keywords) {
            val highlights = highlighter.getTokenHighlights(keyword)
            Assert.assertArrayEquals(
                "Keyword '${keyword}' should map to KEYWORD_KEY",
                arrayOf(ValkyrieSyntaxHighlighter.KEYWORD_KEY),
                highlights
            )
        }
    }

    fun testStringToken() {
        val highlights = highlighter.getTokenHighlights(ValkyrieTypes.LITERAL_STRING)
        Assert.assertArrayEquals(
            "LITERAL_STRING should map to STRING_KEY",
            arrayOf(ValkyrieSyntaxHighlighter.STRING_KEY),
            highlights
        )
    }

    fun testNumberToken() {
        val highlights = highlighter.getTokenHighlights(ValkyrieTypes.LITERAL_NUMBER)
        Assert.assertArrayEquals(
            "LITERAL_NUMBER should map to NUMBER_KEY",
            arrayOf(ValkyrieSyntaxHighlighter.NUMBER_KEY),
            highlights
        )
    }

    fun testIdentifierToken() {
        val highlights = highlighter.getTokenHighlights(ValkyrieTypes.IDENTIFIER)
        Assert.assertArrayEquals(
            "IDENTIFIER should map to IDENTIFIER_KEY",
            arrayOf(ValkyrieSyntaxHighlighter.IDENTIFIER_KEY),
            highlights
        )
    }

    fun testOperatorTokens() {
        val operators = ValkyrieTypes.OPERATORS.types
        Assert.assertTrue("OPERATORS TokenSet should not be empty", operators.isNotEmpty())
        for (op in operators) {
            val highlights = highlighter.getTokenHighlights(op)
            Assert.assertArrayEquals(
                "Operator '${op}' should map to OPERATOR_KEY",
                arrayOf(ValkyrieSyntaxHighlighter.OPERATOR_KEY),
                highlights
            )
        }
    }

    fun testParenthesesTokens() {
        val parens = ValkyrieTypes.PARENTHESES.types
        Assert.assertTrue("PARENTHESES TokenSet should not be empty", parens.isNotEmpty())
        for (paren in parens) {
            val highlights = highlighter.getTokenHighlights(paren)
            Assert.assertArrayEquals(
                "Parenthesis '${paren}' should map to PAREN_KEY",
                arrayOf(ValkyrieSyntaxHighlighter.PAREN_KEY),
                highlights
            )
        }
    }

    fun testCommentToken() {
        val highlights = highlighter.getTokenHighlights(ValkyrieTypes.COMMENT)
        Assert.assertArrayEquals(
            "COMMENT should map to COMMENT_KEY",
            arrayOf(ValkyrieSyntaxHighlighter.COMMENT_KEY),
            highlights
        )
    }

    fun testUnknownTokenReturnsEmpty() {
        val unknownToken = com.intellij.psi.TokenType.BAD_TOKEN
        val highlights = highlighter.getTokenHighlights(unknownToken)
        Assert.assertArrayEquals(
            "Unknown token should return empty array",
            TextAttributesKey.EMPTY_ARRAY,
            highlights
        )
    }
}
