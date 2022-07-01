package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.AnimationParserDefinition
import com.intellij.testFramework.ParsingTestCase

class AnimationParserTest : ParsingTestCase("parser/animation", "animation", false, AnimationParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
