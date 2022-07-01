package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ScriptControlParserTest : ParsingTestCase("parser/script/control", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testControlFlow() {
        doTest(true, true)
    }
}
