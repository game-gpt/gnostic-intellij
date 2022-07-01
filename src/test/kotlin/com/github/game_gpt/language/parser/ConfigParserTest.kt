package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ConfigParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ConfigParserTest : ParsingTestCase("parser/config", "config", false, ConfigParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
