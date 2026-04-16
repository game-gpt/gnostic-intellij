package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class ShaderLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportShaderExtension = true))
    }

    fun testShaderKeyword() {
        doFileTest("lexer/shader/keyword.von")
    }

    fun testShaderClass() {
        doFileTest("lexer/shader/class.von")
    }

    fun testShaderWithProperties() {
        doFileTest("lexer/shader/properties.von")
    }

    fun testShaderWithFunctions() {
        doFileTest("lexer/shader/functions.von")
    }
}
