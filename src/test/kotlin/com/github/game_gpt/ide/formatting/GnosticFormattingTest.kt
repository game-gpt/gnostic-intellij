package com.github.game_gpt.ide.formatting

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Assert

class GnosticFormattingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/formatting"
    }

    fun testVonFormattingDoesNotThrow() {
        val input = "{name: \"test\", count: 42}"
        myFixture.configureByText("test.von", input)
        com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction(project) {
            com.intellij.codeInsight.actions.ReformatCodeProcessor(project, myFixture.file, null, false).run()
        }
        val result = myFixture.editor.document.text
        Assert.assertNotNull("Formatting should produce non-null result", result)
    }
}
