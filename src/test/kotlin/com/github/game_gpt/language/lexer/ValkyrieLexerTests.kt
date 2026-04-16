package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

class ValkyrieLexerTests : LexerTestCase() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig())
    }

    override fun getDirPath(): String {
        return "src/test/testData/lexer/script"
    }

    fun testKeywords() {
        doTest("class fn let if else loop while return true false null trait enum")
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

    fun testOperators() {
        doTest("+ - * / % = ==")
    }

    fun testArrow() {
        doTest("-> =>")
    }

    fun testDoubleColon() {
        doTest("::")
    }

    fun testParentheses() {
        doTest("() {} []")
    }

    fun testPunctuation() {
        doTest("; , . :")
    }

    fun testComment() {
        doTest("# comment")
    }
}
