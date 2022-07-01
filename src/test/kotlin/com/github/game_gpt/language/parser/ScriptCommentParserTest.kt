package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase

class ScriptCommentParserTest : ParsingTestCase("parser/script/comment", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testComments() {
        doTest(true, true)
    }
}
