package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.PrefabParserDefinition
import com.intellij.testFramework.ParsingTestCase

class PrefabParserTest : ParsingTestCase("parser/prefab", "prefab", false, PrefabParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
