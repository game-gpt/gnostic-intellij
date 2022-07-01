package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ShaderDeclarationParserTest : ParsingTestCase("parser/shader/shader", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testShaderDeclaration() {
        doTest(true, true)
    }
}
