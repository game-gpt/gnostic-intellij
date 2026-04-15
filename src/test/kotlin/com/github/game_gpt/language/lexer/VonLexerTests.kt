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
        doTest("# comment")
    }

    fun testStringLiteral() {
        doTest("\"hello\"")
    }

    fun testNumberLiteral() {
        doTest("123 3.14")
    }

    fun testIdentifier() {
        doTest("Player")
    }

    fun testKeywords() {
        doTest("true false null")
    }

    fun testBraces() {
        doTest("{}")
    }

    fun testBrackets() {
        doTest("[]")
    }

    fun testColonAndComma() {
        doTest(": ,")
    }

    fun testEmptyObject() {
        doTest("{}")
    }

    fun testBareTrue() {
        doTest("true")
    }

    fun testBareFalse() {
        doTest("false")
    }

    fun testBareNull() {
        doTest("null")
    }

    fun testKeywordsAsKeys() {
        doTest("{true: true, false: false, null: null}")
    }

    fun testMixedObject() {
        doTest("{name: \"test\", count: 42, active: true}")
    }
}
