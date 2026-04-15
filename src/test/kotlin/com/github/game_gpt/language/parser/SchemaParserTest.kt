package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.SchemaParserDefinition
import com.intellij.testFramework.ParsingTestCase
import java.util.concurrent.TimeUnit

class SchemaParserTest : ParsingTestCase("parser/schema", "schema", false, SchemaParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        val startTime = System.currentTimeMillis()
        val timeout = TimeUnit.SECONDS.toMillis(30)
        
        doTest(true, true)
        
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed > timeout) {
            fail("Test timed out after $elapsed milliseconds")
        }
    }

    fun testAnnotation() {
        doTest("annotation/Annotation")
    }

    fun testSchemaDeclaration() {
        doTest("schema/SchemaDeclaration")
    }

    fun testEnumDeclaration() {
        doTest("enum/EnumDeclaration")
    }

    fun testFieldDeclaration() {
        doTest("field/FieldDeclaration")
    }

    fun testMessageDeclaration() {
        doTest("message/MessageDeclaration")
    }

    fun testModelDeclaration() {
        doTest("model/ModelDeclaration")
    }

    fun testServiceDeclaration() {
        doTest("service/ServiceDeclaration")
    }
}