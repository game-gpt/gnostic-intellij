package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ScriptParserDefinition
import com.intellij.testFramework.ParsingTestCase
import java.util.concurrent.TimeUnit

class ScriptParserTest : ParsingTestCase("parser/script", "script", false, ScriptParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        val startTime = System.currentTimeMillis()
        val timeout = TimeUnit.SECONDS.toMillis(30)
        
        doTest(true, true)
        
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed > timeout) {
            fail("Test timed out after $elapsed milliseconds")
        }
    }
}
