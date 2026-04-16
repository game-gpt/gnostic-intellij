package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class ValkyrieLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig())
    }

    fun testKeywords() {
        doFileTest("lexer/script/keywords.von")
    }

    fun testStringLiteral() {
        doFileTest("lexer/script/string.von")
    }

    fun testNumberLiteral() {
        doFileTest("lexer/script/number.von")
    }

    fun testIdentifier() {
        doFileTest("lexer/script/identifier.von")
    }

    fun testOperators() {
        doFileTest("lexer/script/operators.von")
    }

    fun testArrow() {
        doFileTest("lexer/script/arrow.von")
    }

    fun testDoubleColon() {
        doFileTest("lexer/script/doubleColon.von")
    }

    fun testParentheses() {
        doFileTest("lexer/script/parentheses.von")
    }

    fun testPunctuation() {
        doFileTest("lexer/script/punctuation.von")
    }

    fun testComment() {
        doFileTest("lexer/script/comment.von")
    }
}
