package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ShaderFallbackParserTest : ParsingTestCase("parser/shader/fallback", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testFallbackBlock() {
        doTest(true, true)
    }
}
