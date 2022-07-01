package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.MaterialParserDefinition
import com.intellij.testFramework.ParsingTestCase

class MaterialParserTest : ParsingTestCase("parser/material", "material", false, MaterialParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
