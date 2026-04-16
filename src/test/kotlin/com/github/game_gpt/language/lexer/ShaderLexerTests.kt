package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class ShaderLexerTests : GnosticLexerTestCase() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportShaderExtension = true))
    }

    override fun getTestDataSubPath(): String {
        return "lexer/shader"
    }

    fun testShaderKeyword() {
        doLexerTest("shader")
    }

    fun testShaderClass() {
        doLexerTest("shader MyShader { }")
    }

    fun testShaderWithProperties() {
        doLexerTest("shader Material { let color: vec4 let intensity: f32 }")
    }

    fun testShaderWithFunctions() {
        doLexerTest("shader Light { fn vertex() -> vec4 { } fn fragment() -> vec4 { } }")
    }
}
