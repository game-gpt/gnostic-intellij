package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ObjectParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ObjectParserTest : ParsingTestCase("parser/object", "von", false, ObjectParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
