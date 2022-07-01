package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ScriptFunctionParserTest : ParsingTestCase("parser/script/function", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testFunctionDeclaration() {
        doTest(true, true)
    }
}
