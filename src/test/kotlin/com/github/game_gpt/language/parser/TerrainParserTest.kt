package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.TerrainParserDefinition
import com.intellij.testFramework.ParsingTestCase

class TerrainParserTest : ParsingTestCase("parser/terrain", "terrain", false, TerrainParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
