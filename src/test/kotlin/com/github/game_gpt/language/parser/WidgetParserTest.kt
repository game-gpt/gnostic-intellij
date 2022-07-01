package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.WidgetParserDefinition
import com.intellij.testFramework.ParsingTestCase

class WidgetParserTest : ParsingTestCase("parser/widget", "widget", false, WidgetParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
