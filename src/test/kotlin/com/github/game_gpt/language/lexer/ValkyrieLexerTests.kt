package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class ValkyrieLexerTests : GnosticLexerTestCase() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig())
    }

    override fun getTestDataSubPath(): String {
        return "lexer/script"
    }

    fun testKeywords() {
        doLexerTest("class fn let if else loop while return true false null trait enum")
    }

    fun testStringLiteral() {
        doLexerTest("\"hello\"")
    }

    fun testNumberLiteral() {
        doLexerTest("123 3.14")
    }

    fun testIdentifier() {
        doLexerTest("Player")
    }

    fun testOperators() {
        doLexerTest("+ - * / % = ==")
    }

    fun testArrow() {
        doLexerTest("-> =>")
    }

    fun testDoubleColon() {
        doLexerTest("::")
    }

    fun testParentheses() {
        doLexerTest("() {} []")
    }

    fun testPunctuation() {
        doLexerTest("; , . :")
    }

    fun testComment() {
        doLexerTest("# comment")
    }
}
