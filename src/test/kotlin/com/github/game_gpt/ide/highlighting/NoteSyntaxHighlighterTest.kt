package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.language.types.NoteTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.testFramework.LightPlatformTestCase
import org.junit.Assert

class NoteSyntaxHighlighterTest : LightPlatformTestCase() {

    private val highlighter = NoteSyntaxHighlighter()

    fun testKeywordTokens() {
        val keywords = NoteTypes.KEYWORDS.types
        Assert.assertTrue("KEYWORDS TokenSet should not be empty", keywords.isNotEmpty())
        for (keyword in keywords) {
            val highlights = highlighter.getTokenHighlights(keyword)
            Assert.assertArrayEquals(
                "Keyword '${keyword}' should map to KEYWORD_KEY",
                arrayOf(NoteSyntaxHighlighter.KEYWORD_KEY),
                highlights
            )
        }
    }

    fun testStringToken() {
        val highlights = highlighter.getTokenHighlights(NoteTypes.STRING)
        Assert.assertArrayEquals(
            "STRING should map to STRING_KEY",
            arrayOf(NoteSyntaxHighlighter.STRING_KEY),
            highlights
        )
    }

    fun testNumberToken() {
        val highlights = highlighter.getTokenHighlights(NoteTypes.NUMBER)
        Assert.assertArrayEquals(
            "NUMBER should map to NUMBER_KEY",
            arrayOf(NoteSyntaxHighlighter.NUMBER_KEY),
            highlights
        )
    }

    fun testIdentifierToken() {
        val highlights = highlighter.getTokenHighlights(NoteTypes.IDENTIFIER)
        Assert.assertArrayEquals(
            "IDENTIFIER should map to IDENTIFIER_KEY",
            arrayOf(NoteSyntaxHighlighter.IDENTIFIER_KEY),
            highlights
        )
    }

    fun testCommentToken() {
        val highlights = highlighter.getTokenHighlights(NoteTypes.COMMENT)
        Assert.assertArrayEquals(
            "COMMENT should map to COMMENT_KEY",
            arrayOf(NoteSyntaxHighlighter.COMMENT_KEY),
            highlights
        )
    }

    fun testMarkerTokens() {
        val markers = listOf(NoteTypes.SCENE_MARKER, NoteTypes.CHOICE_MARKER, NoteTypes.PERCENT, NoteTypes.TILDE, NoteTypes.HORIZONTAL_RULE, NoteTypes.DASH)
        for (marker in markers) {
            val highlights = highlighter.getTokenHighlights(marker)
            Assert.assertArrayEquals(
                "Marker '${marker}' should map to MARKER_KEY",
                arrayOf(NoteSyntaxHighlighter.MARKER_KEY),
                highlights
            )
        }
    }

    fun testTextToken() {
        val highlights = highlighter.getTokenHighlights(NoteTypes.TEXT)
        Assert.assertArrayEquals(
            "TEXT should map to TEXT_KEY",
            arrayOf(NoteSyntaxHighlighter.TEXT_KEY),
            highlights
        )
    }

    fun testStructureTokens() {
        val structures = listOf(NoteTypes.STORY_FILE, NoteTypes.SCENE_DEFINITION)
        for (struct in structures) {
            val highlights = highlighter.getTokenHighlights(struct)
            Assert.assertArrayEquals(
                "Structure '${struct}' should map to STRUCTURE_KEY",
                arrayOf(NoteSyntaxHighlighter.STRUCTURE_KEY),
                highlights
            )
        }
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
