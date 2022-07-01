package com.github.game_gpt.language.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

class VonLexerTests : LexerTestCase() {

    override fun createLexer(): Lexer {
        return VonLexer()
    }

    override fun getDirPath(): String {
        return "lexer/von"
    }

    fun testComment() {
        doTest("# comment", "COMMENT ('# comment')")
    }

    fun testStringLiteral() {
        doTest("\"hello\"", "LITERAL_STRING ('\"hello\"')")
    }

    fun testNumberLiteral() {
        doTest("123 3.14", """
            LITERAL_NUMBER ('123')
            WHITE_SPACE (' ')
            LITERAL_NUMBER ('3.14')
        """.trimIndent())
    }

    fun testIdentifier() {
        doTest("Player", "IDENTIFIER ('Player')")
    }

    fun testKeywords() {
        doTest("true false null", """
            true ('true')
            WHITE_SPACE (' ')
            false ('false')
            WHITE_SPACE (' ')
            null ('null')
        """.trimIndent())
    }

    fun testBraces() {
        doTest("{}", """
            { ('{')
            } ('}')
        """.trimIndent())
    }

    fun testBrackets() {
        doTest("[]", """
            [ ('[')
            ] (']')
        """.trimIndent())
    }

    fun testColonAndComma() {
        doTest(": ,", """
            : (':')
            WHITE_SPACE (' ')
            , (',')
        """.trimIndent())
    }

    fun testEmptyObject() {
        doTest("{}", """
            { ('{')
            } ('}')
        """.trimIndent())
    }

    fun testBareTrue() {
        doTest("true", "true ('true')")
    }

    fun testBareFalse() {
        doTest("false", "false ('false')")
    }

    fun testBareNull() {
        doTest("null", "null ('null')")
    }

    fun testKeywordsAsKeys() {
        doTest("{true: true, false: false, null: null}", """
            { ('{')
            true ('true')
            : (':')
            WHITE_SPACE (' ')
            true ('true')
            , (',')
            WHITE_SPACE (' ')
            false ('false')
            : (':')
            WHITE_SPACE (' ')
            false ('false')
            , (',')
            WHITE_SPACE (' ')
            null ('null')
            : (':')
            WHITE_SPACE (' ')
            null ('null')
            } ('}')
        """.trimIndent())
    }

    fun testMixedObject() {
        doTest("{name: \"test\", count: 42, active: true}", """
            { ('{')
            IDENTIFIER ('name')
            : (':')
            WHITE_SPACE (' ')
            LITERAL_STRING ('"test"')
            , (',')
            WHITE_SPACE (' ')
            IDENTIFIER ('count')
            : (':')
            WHITE_SPACE (' ')
            LITERAL_NUMBER ('42')
            , (',')
            WHITE_SPACE (' ')
            IDENTIFIER ('active')
            : (':')
            WHITE_SPACE (' ')
            true ('true')
            } ('}')
        """.trimIndent())
    }
}
