package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.StoryParserDefinition
import com.intellij.testFramework.ParsingTestCase
import java.util.concurrent.TimeUnit

/**
 * Story 解析器测试
 *
 * 测试 Story 格式（基于 Markdown 的游戏脚本语言）的解析功能，
 * 验证各种语法结构能够正确构建树状 AST。
 */
class StoryParserTest : ParsingTestCase("parser/story", "story", false, StoryParserDefinition()) {

    /**
     * 获取测试数据路径
     *
     * @return 测试数据路径
     */
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    /**
     * 测试基本示例解析
     */
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
