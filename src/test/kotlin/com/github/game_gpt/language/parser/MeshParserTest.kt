package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.MeshParserDefinition
import com.intellij.testFramework.ParsingTestCase

class MeshParserTest : ParsingTestCase("parser/mesh", "mesh", false, MeshParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
