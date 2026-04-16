package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.types.VocTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.testFramework.LightPlatformTestCase
import org.junit.Assert

class VocSyntaxHighlighterTest : LightPlatformTestCase() {

    private val highlighter = VocSyntaxHighlighter()

    fun testKeywordTokens() {
        val keywords = VocTypes.KEYWORDS.types
        Assert.assertTrue("KEYWORDS TokenSet should not be empty", keywords.isNotEmpty())
        for (keyword in keywords) {
            val highlights = highlighter.getTokenHighlights(keyword)
            Assert.assertArrayEquals(
                "Keyword '${keyword}' should map to KEYWORD_KEY",
                arrayOf(VocSyntaxHighlighter.KEYWORD_KEY),
                highlights
            )
        }
    }

    fun testTagTokens() {
        val tags = VocTypes.TAG_TOKENS.types
        Assert.assertTrue("TAG_TOKENS TokenSet should not be empty", tags.isNotEmpty())
        for (tag in tags) {
            val highlights = highlighter.getTokenHighlights(tag)
            Assert.assertArrayEquals(
                "Tag token '${tag}' should map to TAG_KEY",
                arrayOf(VocSyntaxHighlighter.TAG_KEY),
                highlights
            )
        }
    }

    fun testStringToken() {
        val highlights = highlighter.getTokenHighlights(VocTypes.LITERAL_STRING)
        Assert.assertArrayEquals(
            "LITERAL_STRING should map to STRING_KEY",
            arrayOf(VocSyntaxHighlighter.STRING_KEY),
            highlights
        )
    }

    fun testNumberToken() {
        val highlights = highlighter.getTokenHighlights(VocTypes.LITERAL_NUMBER)
        Assert.assertArrayEquals(
            "LITERAL_NUMBER should map to NUMBER_KEY",
            arrayOf(VocSyntaxHighlighter.NUMBER_KEY),
            highlights
        )
    }

    fun testIdentifierToken() {
        val highlights = highlighter.getTokenHighlights(VocTypes.IDENTIFIER)
        Assert.assertArrayEquals(
            "IDENTIFIER should map to IDENTIFIER_KEY",
            arrayOf(VocSyntaxHighlighter.IDENTIFIER_KEY),
            highlights
        )
    }

    fun testEqualToken() {
        val highlights = highlighter.getTokenHighlights(VocTypes.EQUAL)
        Assert.assertArrayEquals(
            "EQUAL should map to OPERATOR_KEY",
            arrayOf(VocSyntaxHighlighter.OPERATOR_KEY),
            highlights
        )
    }

    fun testParenthesesTokens() {
        val parens = VocTypes.PARENTHESES.types
        Assert.assertTrue("PARENTHESES TokenSet should not be empty", parens.isNotEmpty())
        for (paren in parens) {
            val highlights = highlighter.getTokenHighlights(paren)
            Assert.assertArrayEquals(
                "Parenthesis '${paren}' should map to PAREN_KEY",
                arrayOf(VocSyntaxHighlighter.PAREN_KEY),
                highlights
            )
        }
    }

    fun testCommentToken() {
        val highlights = highlighter.getTokenHighlights(VocTypes.COMMENT)
        Assert.assertArrayEquals(
            "COMMENT should map to COMMENT_KEY",
            arrayOf(VocSyntaxHighlighter.COMMENT_KEY),
            highlights
        )
    }

    fun testUnknownTokenReturnsEmpty() {
        val unknownToken = com.intellij.psi.TokenType.BAD_CHARACTER
        val highlights = highlighter.getTokenHighlights(unknownToken)
        Assert.assertArrayEquals(
            "Unknown token should return empty array",
            TextAttributesKey.EMPTY_ARRAY,
            highlights
        )
    }
}
