package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.types.VonTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.testFramework.LightPlatformTestCase
import org.junit.Assert

class VonSyntaxHighlighterTest : LightPlatformTestCase() {

    private val highlighter = VonSyntaxHighlighter()

    fun testKeywordTokens() {
        val keywords = VonTypes.KEYWORDS.types
        Assert.assertTrue("KEYWORDS TokenSet should not be empty", keywords.isNotEmpty())
        for (keyword in keywords) {
            val highlights = highlighter.getTokenHighlights(keyword)
            Assert.assertArrayEquals(
                "Keyword '${keyword}' should map to KEYWORD_KEY",
                arrayOf(VonSyntaxHighlighter.KEYWORD_KEY),
                highlights
            )
        }
    }

    fun testStringToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.LITERAL_STRING)
        Assert.assertArrayEquals(
            "LITERAL_STRING should map to STRING_KEY",
            arrayOf(VonSyntaxHighlighter.STRING_KEY),
            highlights
        )
    }

    fun testNumberToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.LITERAL_NUMBER)
        Assert.assertArrayEquals(
            "LITERAL_NUMBER should map to NUMBER_KEY",
            arrayOf(VonSyntaxHighlighter.NUMBER_KEY),
            highlights
        )
    }

    fun testIdentifierToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.IDENTIFIER)
        Assert.assertArrayEquals(
            "IDENTIFIER should map to IDENTIFIER_KEY",
            arrayOf(VonSyntaxHighlighter.IDENTIFIER_KEY),
            highlights
        )
    }

    fun testParenthesesTokens() {
        val parens = VonTypes.PARENTHESES.types
        Assert.assertTrue("PARENTHESES TokenSet should not be empty", parens.isNotEmpty())
        for (paren in parens) {
            val highlights = highlighter.getTokenHighlights(paren)
            Assert.assertArrayEquals(
                "Parenthesis '${paren}' should map to PAREN_KEY",
                arrayOf(VonSyntaxHighlighter.PAREN_KEY),
                highlights
            )
        }
    }

    fun testColonToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.COLON)
        Assert.assertArrayEquals(
            "COLON should map to COLON_KEY",
            arrayOf(VonSyntaxHighlighter.COLON_KEY),
            highlights
        )
    }

    fun testCommaToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.COMMA)
        Assert.assertArrayEquals(
            "COMMA should map to COMMA_KEY",
            arrayOf(VonSyntaxHighlighter.COMMA_KEY),
            highlights
        )
    }

    fun testCommentToken() {
        val highlights = highlighter.getTokenHighlights(VonTypes.COMMENT)
        Assert.assertArrayEquals(
            "COMMENT should map to COMMENT_KEY",
            arrayOf(VonSyntaxHighlighter.COMMENT_KEY),
            highlights
        )
    }

    fun testStructureTokens() {
        val structureTokens = listOf(VonTypes.VON_DICT, VonTypes.VON_LIST, VonTypes.VON_PAIR)
        for (token in structureTokens) {
            val highlights = highlighter.getTokenHighlights(token)
            Assert.assertArrayEquals(
                "Structure token '${token}' should map to STRUCTURE_KEY",
                arrayOf(VonSyntaxHighlighter.STRUCTURE_KEY),
                highlights
            )
        }
    }

    fun testUnknownTokenReturnsEmpty() {
        val unknownToken = com.intellij.psi.TokenType.ERROR_ELEMENT
        val highlights = highlighter.getTokenHighlights(unknownToken)
        Assert.assertArrayEquals(
            "Unknown token should return empty array",
            TextAttributesKey.EMPTY_ARRAY,
            highlights
        )
    }
}
