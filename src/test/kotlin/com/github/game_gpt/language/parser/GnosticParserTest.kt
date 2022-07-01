package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.GnosticParserDefinition
import com.intellij.testFramework.ParsingTestCase

class GnosticParserTest : ParsingTestCase("parser/gnostic", "gnostic", false, GnosticParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
