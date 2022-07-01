package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SceneParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SceneParserTest : ParsingTestCase("parser/scene", "scene", false, SceneParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
