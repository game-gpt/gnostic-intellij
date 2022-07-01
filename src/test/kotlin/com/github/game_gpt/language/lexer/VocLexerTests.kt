package com.github.game_gpt.language.lexer

import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

class VocLexerTests : LexerTestCase() {

    override fun createLexer(): Lexer {
        return VocLexer()
    }

    override fun getDirPath(): String {
        return "lexer/widget"
    }

    fun testTemplateTag() {
        doTest("<template>")
    }

    fun testScriptTag() {
        doTest("<script>")
    }

    fun testStyleTag() {
        doTest("<style>")
    }

    fun testClosingTags() {
        doTest("</template> </script> </style>")
    }

    fun testTagOpenAndClose() {
        doTest("<Layout>")
    }

    fun testSelfClosingTag() {
        doTest("<MenuBar />")
    }

    fun testPropertyAttribute() {
        doTest("orientation=\"vertical\"")
    }

    fun testEventBinding() {
        doTest("onClick={handleClick}")
    }

    fun testSlotContent() {
        doTest("{children}")
    }

    fun testStyleValue() {
        doTest("style=\"flex-1\"")
    }

    fun testComment() {
        doTest("<!-- comment -->")
    }
}
