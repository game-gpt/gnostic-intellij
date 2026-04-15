package com.github.game_gpt.ide.folding

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase

class GnosticFoldingTest : LightJavaCodeInsightFixtureTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/folding"
    }

    fun testVonFolding() {
        myFixture.testFolding("test.von")
    }
}