package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.intellij.lexer.Lexer

class SchemaLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportSchemaExtension = true))
    }

    fun testSchemaKeyword() {
        doFileTest("lexer/schema/keyword.von")
    }

    fun testSchemaBlock() {
        doFileTest("lexer/schema/block.von")
    }

    fun testSchemaWithDialect() {
        doFileTest("lexer/schema/dialect.von")
    }

    fun testEnumDefinition() {
        doFileTest("lexer/schema/enum.von")
    }

    fun testModelDefinition() {
        doFileTest("lexer/schema/model.von")
    }

    fun testMessageDefinition() {
        doFileTest("lexer/schema/message.von")
    }

    fun testServiceDefinition() {
        doFileTest("lexer/schema/service.von")
    }

    fun testNamespaceDeclaration() {
        doFileTest("lexer/schema/namespace.von")
    }

    fun testFieldWithDefault() {
        doFileTest("lexer/schema/fieldDefault.von")
    }

    fun testOptionalField() {
        doFileTest("lexer/schema/optionalField.von")
    }

    fun testReferenceField() {
        doFileTest("lexer/schema/referenceField.von")
    }
}
