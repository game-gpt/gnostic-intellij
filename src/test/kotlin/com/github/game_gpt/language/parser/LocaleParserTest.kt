package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.LocaleParserDefinition
import com.intellij.testFramework.ParsingTestCase

class LocaleParserTest : ParsingTestCase("parser/locale", "locale", false, LocaleParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
