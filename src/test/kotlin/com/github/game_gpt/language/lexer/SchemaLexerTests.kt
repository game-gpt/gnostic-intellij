package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

class SchemaLexerTests : LexerTestCase() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportSchemaExtension = true))
    }

    override fun getDirPath(): String {
        return "lexer/schema"
    }

    fun testSchemaKeyword() {
        doTest("schema")
    }

    fun testSchemaBlock() {
        doTest("schema game_db { }")
    }

    fun testSchemaWithDialect() {
        doTest("schema game_db { dialect: \"postgresql\" }")
    }

    fun testEnumDefinition() {
        doTest("enums ItemRarity { Common = 0 }")
    }

    fun testModelDefinition() {
        doTest("model Player { id: uuid }")
    }

    fun testMessageDefinition() {
        doTest("message GetPlayerRequest { id: uuid }")
    }

    fun testServiceDefinition() {
        doTest("service PlayerService { get_player() -> Player }")
    }

    fun testNamespaceDeclaration() {
        doTest("namespace game_backend;")
    }

    fun testFieldWithDefault() {
        doTest("level: i32 = 1;")
    }

    fun testOptionalField() {
        doTest("email: string?;")
    }

    fun testReferenceField() {
        doTest("inventory: [&PlayerInventory];")
    }
}
