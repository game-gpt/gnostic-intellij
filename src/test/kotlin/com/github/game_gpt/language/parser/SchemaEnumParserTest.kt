package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SchemaEnumParserTest : ParsingTestCase("parser/schema/enum", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testEnumDeclaration() {
        doTest(true, true)
    }
}
