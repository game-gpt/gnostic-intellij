package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ScriptClassParserTest : ParsingTestCase("parser/script/class", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testClassDeclaration() {
        doTest(true, true)
    }
}
