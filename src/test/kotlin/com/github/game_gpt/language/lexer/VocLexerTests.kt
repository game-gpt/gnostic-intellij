package com.github.game_gpt.language.lexer

import com.intellij.lexer.Lexer

class VocLexerTests : GnosticLexerTestCase() {

    override fun createLexer(): Lexer {
        return VocLexer()
    }

    override fun getTestDataSubPath(): String {
        return "lexer/widget"
    }

    fun testTemplateTag() {
        doLexerTest("<template>")
    }

    fun testScriptTag() {
        doLexerTest("<script>")
    }

    fun testStyleTag() {
        doLexerTest("<style>")
    }

    fun testClosingTags() {
        doLexerTest("</template> </script> </style>")
    }

    fun testTagOpenAndClose() {
        doLexerTest("<Layout>")
    }

    fun testSelfClosingTag() {
        doLexerTest("<MenuBar />")
    }

    fun testPropertyAttribute() {
        doLexerTest("orientation=\"vertical\"")
    }

    fun testEventBinding() {
        doLexerTest("onClick={handleClick}")
    }

    fun testSlotContent() {
        doLexerTest("{children}")
    }

    fun testStyleValue() {
        doLexerTest("style=\"flex-1\"")
    }

    fun testComment() {
        doLexerTest("<!-- comment -->")
    }
}
