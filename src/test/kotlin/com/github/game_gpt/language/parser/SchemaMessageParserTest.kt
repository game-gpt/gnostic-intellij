package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SchemaMessageParserTest : ParsingTestCase("parser/schema/message", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testMessageDeclaration() {
        doTest(true, true)
    }
}
