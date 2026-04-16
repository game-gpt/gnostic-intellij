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
import com.intellij.testFramework.fixtures.IdeaTestExecutionPolicy
import junit.framework.TestCase
import java.io.File
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

class VonLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return VonLexer()
    }

    override val dirPath: String = "src/test/testData/lexer/von"

    fun testComment() {
        doFileTest("lexer/von/comment.von")
        doTest("# comment")
    }

    fun testStringLiteral() {
        doTest("\"hello von!\"")
    }

    fun testNumberLiteral() {
        doTest("123 3.14")
    }

    fun testIdentifier() {
        doTest("Player")
    }

    fun testKeywords() {
        doTest("true false null")
    }

    fun testBraces() {
        doTest("[] {}")
    }

    fun testColonAndComma() {
        doTest(":,")
    }

    fun testKeywordsAsKeys() {
        doTest("{true: true, false: false, null: null}")
    }

    fun testMixedObject() {
        doTest("{name: \"test\", count: 42, active: true}")
    }
}



abstract class GnosticLexerTest : UsefulTestCase() {
    protected val expectedFileExtension: String
        get() = ".txt"

    protected fun doFileTest(path: String, lexer: Lexer = createLexer()) {
        val source = FileUtil.loadFile(File(path))
        val expected = getPathToTestDataFile(this.expectedFileExtension)
        doTest(source, expected, lexer)
    }
    protected fun doTest(source: String, expected: String, lexer: Lexer = createLexer()) {
        val result = printTokens(lexer, source, 0)
        assertSameLines(expected, result)
    }

    protected fun printTokens(lexer: Lexer, text: CharSequence, start: Int): String {
        return GnosticLexerTest.Companion.printTokens(text, start, lexer)
    }

    protected fun getPathToTestDataFile(extension: String): String {
        return IdeaTestExecutionPolicy.getHomePathWithPolicy() + "/" + this.dirPath + "/" + getTestName(true) + extension
    }



    protected fun checkZeroState(text: String, tokenTypes: TokenSet) {
        val lexer: Lexer = createLexer()
        lexer.start(text)

        while (true) {
            val type: IElementType? = lexer.getTokenType()
            if (type == null) {
                break
            }
            if (tokenTypes.contains(type) && lexer.getState() !== 0) {
                fail("Non-zero lexer state on token \"" + lexer.getTokenText() + "\" (" + type + ") at " + lexer.getTokenStart())
            }
            lexer.advance()
        }
    }

    protected fun printTokens(text: String, start: Int): String {
        return GnosticLexerTest.Companion.printTokens(text, start, createLexer())
    }

    protected fun checkCorrectRestart(text: String) {
        val mainLexer: Lexer = createLexer()
        val allTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
            GnosticLexerTest.Companion.tokenize(text, 0, 0, mainLexer)
        val auxLexer: Lexer = createLexer()
        auxLexer.start(text)
        var index = 0
        while (true) {
            val type: IElementType? = auxLexer.getTokenType()
            if (type == null) {
                break
            }
            val state: Int = auxLexer.getState()
            if (state == 0 || (auxLexer is RestartableLexer && (auxLexer as RestartableLexer).isRestartableState(state))) {
                val tokenStart: Int = auxLexer.getTokenStart()
                val expectedTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
                    allTokens.subList(index, allTokens.size)
                val restartedTokens: MutableList<Trinity<IElementType?, Int?, Int?>?> =
                    GnosticLexerTest.Companion.tokenize(text, tokenStart, state, mainLexer)
                TestCase.assertEquals(
                    "Restarting impossible from offset " + tokenStart + " - " + auxLexer.getTokenText() + "\n" +
                            "All tokens <type, offset, lexer state>: " + allTokens + "\n",
                    expectedTokens.stream()
                        .map<String?> { o: Trinity<IElementType?, kotlin.Int?, kotlin.Int?>? -> Objects.toString(o) }
                        .collect(
                            Collectors.joining("\n")
                        ),
                    restartedTokens.stream()
                        .map<String?> { o: Trinity<IElementType?, kotlin.Int?, kotlin.Int?>? -> Objects.toString(o) }
                        .collect(
                            Collectors.joining("\n")
                        )
                )
            }
            index++
            auxLexer.advance()
        }
    }


    protected fun loadTestDataFile(fileExt: String): String {
        val fileName = getPathToTestDataFile(fileExt)
        var text = ""
        try {
            val fileText: String = FileUtil.loadFile(File(fileName))
            text = StringUtil.convertLineSeparators(if (shouldTrim()) fileText.trim { it <= ' ' } else fileText)
        } catch (e: IOException) {
            fail("can't load file " + fileName + ": " + e.message)
        }
        return text
    }

    protected fun shouldTrim(): Boolean {
        return true
    }

    protected abstract fun createLexer(): Lexer

    protected abstract val dirPath: String

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
                LOG.error("Restarting impossible from offset " + start, t)
                throw RuntimeException(t)
            }
            while (lexer.getTokenType() != null) {
                allTokens.add(Trinity.create(lexer.getTokenType(), lexer.getTokenStart(), lexer.getState()))
                lexer.advance()
            }
            return allTokens
        }

        fun printTokens(text: CharSequence, start: Int, lexer: Lexer): String {
            lexer.start(text, start, text.length)
            val result = StringBuilder()
            var tokenType: IElementType?
            while ((lexer.getTokenType().also { tokenType = it }) != null) {
                result.append(
                    GnosticLexerTest.Companion.printSingleToken(
                        text,
                        tokenType!!,
                        lexer.getTokenStart(),
                        lexer.getTokenEnd()
                    )
                )
                lexer.advance()
            }
            return result.toString()
        }

        fun printTokens(iterator: HighlighterIterator): String {
            val text: CharSequence = iterator.getDocument().getCharsSequence()
            val result = StringBuilder()
            var tokenType: IElementType
            while (!iterator.atEnd()) {
                tokenType = iterator.getTokenType()
                result.append(
                    GnosticLexerTest.Companion.printSingleToken(
                        text,
                        tokenType,
                        iterator.getStart(),
                        iterator.getEnd()
                    )
                )
                iterator.advance()
            }
            return result.toString()
        }

        fun printSingleToken(fileText: CharSequence, tokenType: IElementType, start: Int, end: Int): String {
            return tokenType.toString() + " ('" + GnosticLexerTest.Companion.getTokenText(
                tokenType,
                fileText,
                start,
                end
            ) + "')\n"
        }

        private fun getTokenText(tokenType: IElementType, sequence: CharSequence, start: Int, end: Int): String {
            return if (tokenType is TokenWrapper)
                (tokenType as TokenWrapper).getText()
            else
                StringUtil.replace(sequence.subSequence(start, end).toString(), "\n", "\\n")
        }
    }
}