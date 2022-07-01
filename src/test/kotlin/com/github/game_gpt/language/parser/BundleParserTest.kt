package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.BundleParserDefinition
import com.intellij.testFramework.ParsingTestCase

class BundleParserTest : ParsingTestCase("parser/bundle", "bundle", false, BundleParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
