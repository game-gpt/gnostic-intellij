package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ShaderVertexParserTest : ParsingTestCase("parser/shader/vertex", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testVertexFunction() {
        doTest(true, true)
    }
}
