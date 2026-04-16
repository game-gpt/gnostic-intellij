package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class ShaderLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportShaderExtension = true))
    }

    override val dirPath: String = "src/test/testData/lexer/shader"

    fun testShaderKeyword() {
        doTest("shader")
    }

    fun testShaderClass() {
        doTest("shader MyShader { }")
    }

    fun testShaderWithProperties() {
        doTest("shader Material { let color: vec4 let intensity: f32 }")
    }

    fun testShaderWithFunctions() {
        doTest("shader Light { fn vertex() -> vec4 { } fn fragment() -> vec4 { } }")
    }
}
