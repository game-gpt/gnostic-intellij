package com.github.game_gpt.language.lexer
import com.intellij.lang.TokenWrapper
import com.intellij.lexer.Lexer
import com.intellij.lexer.RestartableLexer
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.intellij.openapi.util.Trinity
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.testFramework.UsefulTestCase
import junit.framework.TestCase
import java.io.File
import java.util.*
import java.util.stream.Collectors

class VonLexerTests : GnosticLexerTest() {
    override val dirPath: String = "src/test/testData/lexer/von"

    override val sourceFileExtension: String
        get() = ".von"

    override fun createLexer(): Lexer {
        return VonLexer()
    }

    fun testComment() {
        doFileTest()
    }

    fun testStringLiteral() {
        doFileTest()
    }

    fun testNumberLiteral() {
        doFileTest()
    }

    fun testIdentifier() {
        doFileTest()
    }

    fun testKeywords() {
        doFileTest()
    }

    fun testBraces() {
        doFileTest()
    }

    fun testColonAndComma() {
        doFileTest()
    }

    fun testKeywordsAsKeys() {
        doFileTest()
    }

    fun testMixedObject() {
        doFileTest()
    }
}



abstract class GnosticLexerTest : UsefulTestCase() {
    protected val expectedFileExtension: String
        get() = ".txt"

    protected var refreshExpected: Boolean = false

    protected abstract fun createLexer(): Lexer

    protected fun doFileTest(path: String, lexer: Lexer = createLexer()) {
        val sourceFilePath = getTestDataPath() + "/" + path
        val source = FileUtil.loadFile(File(sourceFilePath))
        val expectedFilePath = sourceFilePath + expectedFileExtension
        val expectedFile = File(expectedFilePath)
        val result = printTokens(lexer, source, 0)
        if (refreshExpected || !expectedFile.exists()) {
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




    protected fun checkZeroState(text: String, tokenTypes: TokenSet) {
        val lexer: Lexer = createLexer()
        lexer.start(text)

        while (true) {
            val type: IElementType = lexer.tokenType ?: break
            if (tokenTypes.contains(type) && lexer.state !== 0) {
                fail("Non-zero lexer state on token \"" + lexer.tokenText + "\" (" + type + ") at " + lexer.tokenStart)
            }
            lexer.advance()
        }
    }

    protected fun printTokens(text: String, start: Int): String {
        return printTokens(text, start, createLexer())
    }

    protected fun checkCorrectRestart(text: String) {
        val mainLexer: Lexer = createLexer()
        val allTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
            tokenize(text, 0, 0, mainLexer)
        val auxLexer: Lexer = createLexer()
        auxLexer.start(text)
        var index = 0
        while (true) {
            val type: IElementType = auxLexer.tokenType ?: break
            val state: Int = auxLexer.state
            if (state == 0 || (auxLexer is RestartableLexer && (auxLexer as RestartableLexer).isRestartableState(state))) {
                val tokenStart: Int = auxLexer.tokenStart
                val expectedTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
                    allTokens.subList(index, allTokens.size)
                val restartedTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
                    tokenize(text, tokenStart, state, mainLexer)
                TestCase.assertEquals(
                    "Restarting impossible from offset " + tokenStart + " - " + auxLexer.tokenText + "\n" +
                            "All tokens <type, offset, lexer state>: " + allTokens + "\n",
                    expectedTokens.stream()
                        .map<String?> { o: Trinity<IElementType?, Int?, Int?>? -> Objects.toString(o) }
                        .collect(
                            Collectors.joining("\n")
                        ),
                    restartedTokens.stream()
                        .map<String?> { o: Trinity<IElementType?, Int?, Int?>? -> Objects.toString(o) }
                        .collect(
                            Collectors.joining("\n")
                        )
                )
            }
            index++
            auxLexer.advance()
        }
    }

    override fun getTestDirectoryName(): String {
        return super.getTestDirectoryName()
    }

    companion object {
        private fun tokenize(
            text: String,
            start: Int,
            state: Int,
            lexer: Lexer
        ): MutableList<Trinity<IElementType?, Int?, Int?>?> {
            val allTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
                ArrayList<Trinity<IElementType?, Int?, Int?>?>()
            try {
                lexer.start(text, start, text.length, state)
            } catch (t: Throwable) {
                LOG.error("Restarting impossible from offset $start", t)
                throw RuntimeException(t)
            }
            while (lexer.tokenType != null) {
                allTokens.add(Trinity.create(lexer.tokenType, lexer.tokenStart, lexer.state))
                lexer.advance()
            }
            return allTokens
        }

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

        fun printTokens(iterator: HighlighterIterator): String {
            val text: CharSequence = iterator.document.charsSequence
            val result = StringBuilder()
            var tokenType: IElementType
            while (!iterator.atEnd()) {
                tokenType = iterator.tokenType
                result.append(
                    printSingleToken(
                        text,
                        tokenType,
                        iterator.start,
                        iterator.end
                    )
                )
                iterator.advance()
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