package com.github.game_gpt.ide.definitions

import com.github.game_gpt.ide.config.NotedownLanguageConfig
import com.github.game_gpt.ide.file.GnosticStoryFile
import com.github.game_gpt.language.GnosticStoryLanguage
import com.github.game_gpt.language.elements.GnosticElementFactory
import com.github.game_gpt.language.lexer.NoteLexer
import com.github.game_gpt.language.parser.NoteParser
import com.github.game_gpt.language.types.NoteTypes
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

/**
 * Notedown 语言的解析器定义，实现 IntelliJ 的 ParserDefinition 接口。
 */
class NotedownParserDefinition : ParserDefinition {

    /**
     * 创建 Notedown 语言的词法分析器。
     */
    override fun createLexer(project: Project): Lexer {
        return NoteLexer(NotedownLanguageConfig(supportXmlExtension = true))
    }

    /**
     * 创建 Notedown 语言的语法解析器。
     */
    override fun createParser(project: Project): PsiParser {
        return NoteParser(NotedownLanguageConfig(supportXmlExtension = true))
    }

    /**
     * 获取文件节点的元素类型。
     */
    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    /**
     * 获取注释类型的 Token 集合。
     */
    override fun getCommentTokens(): TokenSet {
        return NoteTypes.COMMENTS
    }

    /**
     * 获取字符串字面量类型的 Token 集合。
     */
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(NoteTypes.STRING)
    }

    /**
     * 根据 AST 节点创建对应的 PSI 元素。
     */
    override fun createElement(node: ASTNode): PsiElement {
        return GnosticElementFactory.createElement(node)
    }

    /**
     * 根据文件视图提供者创建 Notedown 语言的 PSI 文件。
     */
    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GnosticStoryFile(viewProvider)
    }

    /**
     * 获取空白字符类型的 Token 集合。
     */
    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.create(TokenType.WHITE_SPACE)
    }

    companion object {

        /**
         * Notedown 语言的文件元素类型。
         */
        val FILE = IFileElementType(GnosticStoryLanguage)
    }
}
