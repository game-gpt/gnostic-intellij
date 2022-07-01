package com.github.game_gpt.ide.definitions

import com.github.game_gpt.ide.file.GnosticWidgetFile
import com.github.game_gpt.language.GnosticWidgetLanguage
import com.github.game_gpt.language.lexer.VocLexer
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.github.game_gpt.language.elements.GnosticElementFactory
import com.github.game_gpt.language.parser.VocParser
import com.intellij.psi.tree.IFileElementType
import com.github.game_gpt.language.types.VocTypes
import com.intellij.psi.tree.TokenSet

class WidgetParserDefinition : ParserDefinition {
    override fun createLexer(project: Project): Lexer {
        return VocLexer()
    }

    override fun createParser(project: Project): PsiParser {
        return VocParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return VocTypes.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(VocTypes.LITERAL_STRING)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return GnosticElementFactory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GnosticWidgetFile(viewProvider)
    }

    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.create(TokenType.WHITE_SPACE)
    }

    companion object {
        val FILE = IFileElementType(GnosticWidgetLanguage)
    }
}
