package com.github.game_gpt.ide.definitions

import com.github.game_gpt.ide.file.GnosticSchemaFile
import com.github.game_gpt.language.GnosticSchemaLanguage
import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.github.game_gpt.language.lexer.ValkyrieLexer
import com.github.game_gpt.language.elements.GnosticElementFactory
import com.github.game_gpt.language.parser.ValkyrieParser
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class SchemaParserDefinition : ParserDefinition {
    override fun createLexer(project: Project): Lexer {
        return ValkyrieLexer(ValkyrieLanguageConfig(supportSchemaExtension = true))
    }

    override fun createParser(project: Project): PsiParser {
        return ValkyrieParser(ValkyrieLanguageConfig(supportSchemaExtension = true))
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return ValkyrieTypes.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(ValkyrieTypes.LITERAL_STRING)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return GnosticElementFactory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GnosticSchemaFile(viewProvider)
    }

    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.create(TokenType.WHITE_SPACE)
    }

    companion object {
        val FILE = IFileElementType(GnosticSchemaLanguage)
    }
}

