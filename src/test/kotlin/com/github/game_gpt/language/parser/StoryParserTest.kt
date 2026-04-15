package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.StoryParserDefinition
import com.intellij.testFramework.ParsingTestCase

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
        doTest(true, true)
    }

    /**
     * 测试注释语法
     */
    fun testComment() {
        doTest(true, true)
    }

    /**
     * 测试变量定义
     */
    fun testVariable() {
        doTest(true, true)
    }

    /**
     * 测试场景定义
     */
    fun testScene() {
        doTest(true, true)
    }

    /**
     * 测试对话行
     */
    fun testDialogue() {
        doTest(true, true)
    }

    /**
     * 测试选项分支
     */
    fun testChoice() {
        doTest(true, true)
    }

    /**
     * 测试条件选项
     */
    fun testConditionalChoice() {
        doTest(true, true)
    }

    /**
     * 测试跳转语句
     */
    fun testJump() {
        doTest(true, true)
    }

    /**
     * 测试条件判断
     */
    fun testConditional() {
        doTest(true, true)
    }

    /**
     * 测试命令调用
     */
    fun testCommand() {
        doTest(true, true)
    }

    /**
     * 测试包含语句
     */
    fun testInclude() {
        doTest(true, true)
    }

    /**
     * 测试分隔线
     */
    fun testSeparator() {
        doTest(true, true)
    }

    /**
     * 测试原生 XML
     */
    fun testXml() {
        doTest(true, true)
    }
}
