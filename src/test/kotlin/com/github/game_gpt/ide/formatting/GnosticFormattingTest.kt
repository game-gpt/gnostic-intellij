package com.github.game_gpt.ide.formatting

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

class GnosticFormattingTest : LightJavaCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/formatting"
    }

    fun testVonFormattingDoesNotThrow() {
        val input = "{name: \"test\", count: 42}"
        myFixture.configureByText("test.von", input)
        myFixture.performAction("ReformatCode")
        val result = myFixture.editor.document.text
        Assert.assertNotNull("Formatting should produce non-null result", result)
    }
}
