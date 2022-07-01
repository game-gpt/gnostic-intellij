package com.github.game_gpt.ide.definitions

import com.github.game_gpt.ide.file.GnosticMaterialFile
import com.github.game_gpt.language.GnosticMaterialLanguage
import com.github.game_gpt.language.lexer.VonLexer
import com.github.game_gpt.language.elements.GnosticElementFactory
import com.github.game_gpt.language.parser.VonParser
import com.github.game_gpt.language.types.VonTypes
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

class MaterialParserDefinition : ParserDefinition {
    override fun createLexer(project: Project): Lexer {
        return VonLexer()
    }

    override fun createParser(project: Project): PsiParser {
        return VonParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return VonTypes.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(VonTypes.LITERAL_STRING)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return GnosticElementFactory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GnosticMaterialFile(viewProvider)
    }

    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.create(TokenType.WHITE_SPACE)
    }

    companion object {
        val FILE = IFileElementType(GnosticMaterialLanguage)
    }
}