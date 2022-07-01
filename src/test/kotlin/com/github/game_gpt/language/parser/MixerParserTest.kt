package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.MixerParserDefinition
import com.intellij.testFramework.ParsingTestCase

class MixerParserTest : ParsingTestCase("parser/mixer", "mixer", false, MixerParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
