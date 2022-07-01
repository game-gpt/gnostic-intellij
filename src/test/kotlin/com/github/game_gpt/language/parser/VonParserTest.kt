package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ObjectParserDefinition
import com.intellij.testFramework.ParsingTestCase

class VonParserTest : ParsingTestCase("parser/von", "von", false, ObjectParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        return super.getTestName(lowercaseFirstLetter)
    }

    fun testSample() {
        doTest(true, true)
    }

    fun testEmptyObject() {
        doTest(true, true)
    }

    fun testBareTrue() {
        doTest(true, true)
    }

    fun testBareFalse() {
        doTest(true, true)
    }

    fun testBareNull() {
        doTest(true, true)
    }

    fun testKeywordsAsKeys() {
        doTest(true, true)
    }
}
