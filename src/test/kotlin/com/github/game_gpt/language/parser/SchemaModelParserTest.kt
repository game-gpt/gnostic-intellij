package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SchemaModelParserTest : ParsingTestCase("parser/schema/model", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testModelDeclaration() {
        doTest(true, true)
    }
}
