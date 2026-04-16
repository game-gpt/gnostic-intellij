package com.github.game_gpt.language.lexer
import com.intellij.lang.TokenWrapper
import com.intellij.lexer.Lexer
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.tree.IElementType
import com.intellij.testFramework.UsefulTestCase
import java.io.File

class VonLexerTests : GnosticLexerTest() {

    override val forceRefresh: Boolean = true

    override fun createLexer(): Lexer {
        return VonLexer()
    }

    override fun getTestDirectoryName(): String {
        return "lexer/von"
    }

    fun testComment() {
        doFileTest("comment.von")
    }

    fun testStringLiteral() {
        doFileTest("stringLiteral.von")
    }

    fun testNumberLiteral() {
        doFileTest("numberLiteral.von")
    }

    fun testIdentifier() {
        doFileTest("identifier.von")
    }

    fun testKeywords() {
        doFileTest("keywords.von")
    }

    fun testBraces() {
        doFileTest("braces.von")
    }

    fun testColonAndComma() {
        doFileTest("colonAndComma.von")
    }

    fun testKeywordsAsKeys() {
        doFileTest("keywordsAsKeys.von")
    }

    fun testMixedObject() {
        doFileTest("mixedObject.von")
    }
}



abstract class GnosticLexerTest : UsefulTestCase() {
    protected open val forceRefresh: Boolean = false
    protected open val expectedFileExtension: String = ".txt"
    protected abstract fun createLexer(): Lexer

    protected fun doFileTest(path: String, lexer: Lexer = createLexer()) {
        val testBasePath = "src/test/testData"
        val sourceFilePath = "$testBasePath/$testDirectoryName/$path"
        val source = FileUtil.loadFile(File(sourceFilePath))
        val expectedFilePath = sourceFilePath + expectedFileExtension
        val expectedFile = File(expectedFilePath)
        val result = printTokens(lexer, source, 0)
        if (forceRefresh || !expectedFile.exists()) {
            FileUtil.writeToFile(expectedFile, result)
        }
        assertSameLinesWithFile(expectedFilePath, result)
    }

    protected fun doTest(source: String, expected: String, lexer: Lexer = createLexer()) {
        val result = printTokens(lexer, source, 0)
        assertSameLines(expected, result)
    }

    protected fun printTokens(lexer: Lexer, text: CharSequence, start: Int): String {
        return printTokens(text, start, lexer)
    }


    companion object {

        fun printTokens(text: CharSequence, start: Int, lexer: Lexer): String {
            lexer.start(text, start, text.length)
            val result = StringBuilder()
            var tokenType: IElementType?
            while ((lexer.tokenType.also { tokenType = it }) != null) {
                result.append(
                    printSingleToken(
                        text,
                        tokenType!!,
                        lexer.tokenStart,
                        lexer.tokenEnd
                    )
                )
                lexer.advance()
            }
            return result.toString()
        }

        fun printSingleToken(fileText: CharSequence, tokenType: IElementType, start: Int, end: Int): String {
            return "$tokenType ('" + getTokenText(
                tokenType,
                fileText,
                start,
                end
            ) + "')\n"
        }

        private fun getTokenText(tokenType: IElementType, sequence: CharSequence, start: Int, end: Int): String {
            return if (tokenType is TokenWrapper) {
                tokenType.text
            } else {
                StringUtil.replace(sequence.subSequence(start, end).toString(), "\n", "\\n")
            }
        }
    }
}