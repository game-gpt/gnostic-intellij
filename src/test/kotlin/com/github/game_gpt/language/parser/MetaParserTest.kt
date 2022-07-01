package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.MetaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class MetaParserTest : ParsingTestCase("parser/meta", "meta", false, MetaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
