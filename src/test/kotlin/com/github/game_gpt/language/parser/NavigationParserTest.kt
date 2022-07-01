package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.NavigationParserDefinition
import com.intellij.testFramework.ParsingTestCase

class NavigationParserTest : ParsingTestCase("parser/navigation", "navigation", false, NavigationParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
