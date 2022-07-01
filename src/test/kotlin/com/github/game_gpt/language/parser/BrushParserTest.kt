package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.BrushParserDefinition
import com.intellij.testFramework.ParsingTestCase

class BrushParserTest : ParsingTestCase("parser/brush", "brush", false, BrushParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
