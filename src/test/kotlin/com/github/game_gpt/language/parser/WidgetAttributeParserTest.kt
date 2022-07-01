package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.WidgetParserDefinition
import com.intellij.testFramework.ParsingTestCase

class WidgetAttributeParserTest : ParsingTestCase("parser/widget/attribute", "widget", false, WidgetParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testWidgetAttribute() {
        doTest(true, true)
    }
}
