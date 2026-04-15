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

    fun testWidgetAttribute() {
        doTest("attribute/WidgetAttribute")
    }

    fun testWidgetComment() {
        doTest("comment/WidgetComment")
    }

    fun testWidgetElement() {
        doTest("element/WidgetElement")
    }

    fun testWidgetExpression() {
        doTest("expression/WidgetExpression")
    }

    fun testScriptSection() {
        doTest("script/ScriptSection")
    }

    fun testStyleSection() {
        doTest("style/StyleSection")
    }

    fun testTemplateSection() {
        doTest("template/TemplateSection")
    }
}