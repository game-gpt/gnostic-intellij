package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.WidgetParserDefinition
import com.intellij.testFramework.ParsingTestCase

class WidgetStyleParserTest : ParsingTestCase("parser/widget/style", "widget", false, WidgetParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testStyleSection() {
        doTest(true, true)
    }
}
