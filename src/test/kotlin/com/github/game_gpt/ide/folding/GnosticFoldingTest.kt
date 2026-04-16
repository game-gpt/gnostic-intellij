package com.github.game_gpt.ide.folding

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class GnosticFoldingTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/folding"
    }

    fun testVonFolding() {
        myFixture.testFolding("test.von")
    }
}
