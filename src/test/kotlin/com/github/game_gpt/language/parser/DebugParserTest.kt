package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.config.NotedownLanguageConfig
import com.github.game_gpt.ide.definitions.StoryParserDefinition
import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase
import com.intellij.psi.PsiFile
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType

class DebugParserTest : LightJavaCodeInsightFixtureTestCase() {

    fun testDebugComment() {
        val content = """# 这是单行注释
# 注释可以包含中文和 English
# 注释可以包含特殊字符：！@#￥%"""
        debugParse("comment.story", content)
    }

    fun testDebugVariable() {
        val content = """{player_name = "主角"}
{sakura_affection = 0}
{knows_secret = false}
{items = [sword, shield, potion]}"""
        debugParse("variable.story", content)
    }

    fun testDebugChoice() {
        val content = """* [选项一]
    这是选项一的内容。
    -> route_a

* [选项二]
    这是选项二的内容。
    -> route_b"""
        debugParse("choice.story", content)
    }

    fun testDebugConditionalChoice() {
        val content = """* {affection >= 5} [特殊选项]
    这是特殊选项。
    -> special_route

* [普通选项]
    这是普通选项。
    -> normal_route"""
        debugParse("conditional_choice.story", content)
    }

    private fun debugParse(fileName: String, content: String) {
        val config = NotedownLanguageConfig(supportXmlExtension = true)
        val parserDefinition = StoryParserDefinition(config)
        
        myFixture.configureByText(fileName, content)
        val file = myFixture.file
        
        println("=== $fileName ===")
        println("FILE")
        printAst(file.node, "  ")
        println()
    }

    private fun printAst(node: ASTNode, indent: String = "") {
        val type = node.elementType
        val text = node.text.replace("\n", "\\n").replace("\r", "\\r")
        
        val typeName = when {
            type == TokenType.WHITE_SPACE -> "PsiWhiteSpace"
            type == TokenType.BAD_CHARACTER -> "PsiElement(BAD_CHARACTER)"
            else -> {
                val typeStr = type.toString()
                when {
                    typeStr == "COMMENT" -> "PsiComment(COMMENT)"
                    typeStr.startsWith("NoteTokenType.") -> "PsiElement(${typeStr.removePrefix("NoteTokenType.")})"
                    else -> "GnosticElement($typeStr)"
                }
            }
        }
        
        if (node.firstChildNode == null) {
            println("$indent$typeName('$text')")
        } else {
            println("$indent$typeName")
            var child = node.firstChildNode
            while (child != null) {
                printAst(child, "$indent  ")
                child = child.treeNext
            }
        }
    }
}
