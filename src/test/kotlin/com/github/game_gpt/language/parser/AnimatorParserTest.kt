package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.AnimatorParserDefinition
import com.intellij.testFramework.ParsingTestCase

class AnimatorParserTest : ParsingTestCase("parser/animator", "animator", false, AnimatorParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
