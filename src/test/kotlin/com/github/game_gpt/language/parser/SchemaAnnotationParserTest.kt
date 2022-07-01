package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase

class SchemaAnnotationParserTest : ParsingTestCase("parser/schema/annotation", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testAnnotation() {
        doTest(true, true)
    }
}
