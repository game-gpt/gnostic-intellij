package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.AssetParserDefinition
import com.intellij.testFramework.ParsingTestCase

class AssetsParserTest : ParsingTestCase("parser/asset", "asset", false, AssetParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        doTest(true, true)
    }
}
