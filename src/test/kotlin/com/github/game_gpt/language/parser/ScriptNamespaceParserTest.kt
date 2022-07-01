package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ScriptNamespaceParserTest : ParsingTestCase("parser/script/namespace", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testNamespaceDeclaration() {
        doTest(true, true)
    }
}
