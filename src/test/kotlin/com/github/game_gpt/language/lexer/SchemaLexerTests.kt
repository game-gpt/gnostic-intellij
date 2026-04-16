package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class SchemaLexerTests : GnosticLexerTestCase() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportSchemaExtension = true))
    }

    override fun getTestDataSubPath(): String {
        return "lexer/schema"
    }

    fun testSchemaKeyword() {
        doLexerTest("schema")
    }

    fun testSchemaBlock() {
        doLexerTest("schema game_db { }")
    }

    fun testSchemaWithDialect() {
        doLexerTest("schema game_db { dialect: \"postgresql\" }")
    }

    fun testEnumDefinition() {
        doLexerTest("enums ItemRarity { Common = 0 }")
    }

    fun testModelDefinition() {
        doLexerTest("model Player { id: uuid }")
    }

    fun testMessageDefinition() {
        doLexerTest("message GetPlayerRequest { id: uuid }")
    }

    fun testServiceDefinition() {
        doLexerTest("service PlayerService { get_player() -> Player }")
    }

    fun testNamespaceDeclaration() {
        doLexerTest("namespace game_backend;")
    }

    fun testFieldWithDefault() {
        doLexerTest("level: i32 = 1;")
    }

    fun testOptionalField() {
        doLexerTest("email: string?;")
    }

    fun testReferenceField() {
        doLexerTest("inventory: [&PlayerInventory];")
    }
}
