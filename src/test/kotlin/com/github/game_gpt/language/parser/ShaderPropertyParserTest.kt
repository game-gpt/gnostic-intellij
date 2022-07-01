package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ShaderPropertyParserTest : ParsingTestCase("parser/shader/property", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testShaderProperty() {
        doTest(true, true)
    }
}
