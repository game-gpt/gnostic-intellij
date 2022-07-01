package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ShaderRenderStatesParserTest : ParsingTestCase("parser/shader/render_states", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testRenderStates() {
        doTest(true, true)
    }
}
