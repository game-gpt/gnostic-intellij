package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SchemaFieldParserTest : ParsingTestCase("parser/schema/field", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testFieldDeclaration() {
        doTest(true, true)
    }
}
