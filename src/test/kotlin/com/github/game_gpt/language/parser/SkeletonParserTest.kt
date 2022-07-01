package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SkeletonParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SkeletonParserTest : ParsingTestCase("parser/skeleton", "skeleton", false, SkeletonParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
