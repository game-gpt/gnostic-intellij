package com.github.game_gpt.language.lexer

import com.intellij.lexer.Lexer

class VocLexerTests : GnosticLexerTest() {

    override fun createLexer(): Lexer {
        return VocLexer()
    }

    fun testTemplateTag() {
        doFileTest("lexer/widget/template.voc")
    }

    fun testScriptTag() {
        doFileTest("lexer/widget/script.voc")
    }

    fun testStyleTag() {
        doFileTest("lexer/widget/style.voc")
    }

    fun testClosingTags() {
        doFileTest("lexer/widget/closing.voc")
    }

    fun testTagOpenAndClose() {
        doFileTest("lexer/widget/tagOpenClose.voc")
    }

    fun testSelfClosingTag() {
        doFileTest("lexer/widget/selfClosing.voc")
    }

    fun testPropertyAttribute() {
        doFileTest("lexer/widget/property.voc")
    }

    fun testEventBinding() {
        doFileTest("lexer/widget/event.voc")
    }

    fun testSlotContent() {
        doFileTest("lexer/widget/slot.voc")
    }

    fun testStyleValue() {
        doFileTest("lexer/widget/styleValue.voc")
    }

    fun testComment() {
        doFileTest("lexer/widget/comment.voc")
    }
}
