package com.github.game_gpt.language.lexer

import com.intellij.lexer.Lexer
import com.intellij.openapi.util.text.StringUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.UsefulTestCase
import java.io.File

abstract class GnosticLexerTestCase : BasePlatformTestCase() {

    protected abstract fun createLexer(): Lexer

    protected fun doLexerTest(text: String) {
        val lexer = createLexer()
        val result = StringBuilder()
        lexer.start(text)
        while (lexer.tokenType != null) {
            result.append(lexer.tokenType.toString())
                .append(" ('")
                .append(StringUtil.escapeStringCharacters(lexer.tokenText))
                .append("')")
                .append("\n")
            lexer.advance()
        }

        val testName = getTestName(true)
        val testDataPath = File("src/test/testData", getTestDataSubPath())
        val expectedFile = File(testDataPath, "$testName.txt")

        if (!expectedFile.exists()) {
            expectedFile.parentFile.mkdirs()
            expectedFile.writeText(result.toString())
            org.junit.Assert.fail("Expected file not found. Created: ${expectedFile.absolutePath}")
        }

        UsefulTestCase.assertSameLinesWithFile(expectedFile.absolutePath, result.toString())
    }

    protected abstract fun getTestDataSubPath(): String
}
