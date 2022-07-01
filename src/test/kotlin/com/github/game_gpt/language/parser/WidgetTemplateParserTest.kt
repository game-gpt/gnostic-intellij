package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.WidgetParserDefinition
import com.intellij.testFramework.ParsingTestCase

class WidgetTemplateParserTest : ParsingTestCase("parser/widget/template", "widget", false, WidgetParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testTemplateSection() {
        doTest(true, true)
    }
}
