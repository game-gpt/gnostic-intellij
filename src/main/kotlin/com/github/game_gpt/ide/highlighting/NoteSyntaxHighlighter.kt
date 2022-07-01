package com.github.game_gpt.ide.highlighting

import com.github.game_gpt.ide.config.NotedownLanguageConfig
import com.github.game_gpt.language.lexer.NoteLexer
import com.github.game_gpt.language.types.NoteTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

/**
 * Notedown语言的语法高亮器，负责将词法单元映射到对应的文本属性键以实现语法着色。
 */
class NoteSyntaxHighlighter : SyntaxHighlighter {

    /**
     * 返回Notedown语言的高亮词法分析器实例。
     *
     * @return Notedown词法分析器
     */
    override fun getHighlightingLexer(): Lexer {
        return NoteLexer(NotedownLanguageConfig(supportXmlExtension = true, supportRawExtension = true))
    }

    /**
     * 根据词法单元类型返回对应的高亮属性键数组。
     *
     * @param tokenType 词法单元类型
     * @return 对应的文本属性键数组
     */
    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            in NoteTypes.KEYWORDS -> arrayOf(KEYWORD_KEY)
            NoteTypes.STRING -> arrayOf(STRING_KEY)
            NoteTypes.NUMBER -> arrayOf(NUMBER_KEY)
            NoteTypes.IDENTIFIER -> arrayOf(IDENTIFIER_KEY)
            NoteTypes.COMMENT -> arrayOf(COMMENT_KEY)
            NoteTypes.SCENE_MARKER -> arrayOf(MARKER_KEY)
            NoteTypes.CHOICE_MARKER -> arrayOf(MARKER_KEY)
            NoteTypes.ARROW -> arrayOf(KEYWORD_KEY)
            NoteTypes.PERCENT -> arrayOf(MARKER_KEY)
            NoteTypes.TILDE -> arrayOf(MARKER_KEY)
            NoteTypes.DOUBLE_COLON -> arrayOf(MARKER_KEY)
            NoteTypes.HORIZONTAL_RULE -> arrayOf(MARKER_KEY)
            NoteTypes.DASH -> arrayOf(MARKER_KEY)
            NoteTypes.BRACE_L, NoteTypes.BRACE_R -> arrayOf(BRACE_KEY)
            NoteTypes.BRACKET_L, NoteTypes.BRACKET_R -> arrayOf(BRACKET_KEY)
            NoteTypes.PAREN_L, NoteTypes.PAREN_R -> arrayOf(PAREN_KEY)
            NoteTypes.COLON -> arrayOf(COLON_KEY)
            NoteTypes.COMMA -> arrayOf(COMMA_KEY)
            in NoteTypes.OPERATORS -> arrayOf(OPERATOR_KEY)
            NoteTypes.TEXT -> arrayOf(TEXT_KEY)
            NoteTypes.STORY_FILE,
            NoteTypes.SCENE_DEFINITION -> arrayOf(STRUCTURE_KEY)

            else -> TextAttributesKey.EMPTY_ARRAY
        }
    }

    companion object {

        /** 关键字高亮属性键 */
        val KEYWORD_KEY = createTextAttributesKey(
            "NOTEDOWN_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        /** 字符串字面量高亮属性键 */
        val STRING_KEY = createTextAttributesKey(
            "NOTEDOWN_STRING",
            DefaultLanguageHighlighterColors.STRING
        )

        /** 数字字面量高亮属性键 */
        val NUMBER_KEY = createTextAttributesKey(
            "NOTEDOWN_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )

        /** 标识符高亮属性键 */
        val IDENTIFIER_KEY = createTextAttributesKey(
            "NOTEDOWN_IDENTIFIER",
            DefaultLanguageHighlighterColors.CLASS_NAME
        )

        /** 注释高亮属性键 */
        val COMMENT_KEY = createTextAttributesKey(
            "NOTEDOWN_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        /** 标记符号高亮属性键（场景标记、选择标记等特殊符号） */
        val MARKER_KEY = createTextAttributesKey(
            "NOTEDOWN_MARKER",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        /** 花括号高亮属性键 */
        val BRACE_KEY = createTextAttributesKey(
            "NOTEDOWN_BRACE",
            DefaultLanguageHighlighterColors.BRACES
        )

        /** 方括号高亮属性键 */
        val BRACKET_KEY = createTextAttributesKey(
            "NOTEDOWN_BRACKET",
            DefaultLanguageHighlighterColors.BRACKETS
        )

        /** 圆括号高亮属性键 */
        val PAREN_KEY = createTextAttributesKey(
            "NOTEDOWN_PAREN",
            DefaultLanguageHighlighterColors.PARENTHESES
        )

        /** 冒号高亮属性键 */
        val COLON_KEY = createTextAttributesKey(
            "NOTEDOWN_COLON",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        /** 逗号高亮属性键 */
        val COMMA_KEY = createTextAttributesKey(
            "NOTEDOWN_COMMA",
            DefaultLanguageHighlighterColors.COMMA
        )

        /** 运算符高亮属性键 */
        val OPERATOR_KEY = createTextAttributesKey(
            "NOTEDOWN_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        /** 文本内容高亮属性键 */
        val TEXT_KEY = createTextAttributesKey(
            "NOTEDOWN_TEXT",
            DefaultLanguageHighlighterColors.STRING
        )

        /** 结构节点高亮属性键（故事文件、场景定义等AST节点） */
        val STRUCTURE_KEY = createTextAttributesKey(
            "NOTEDOWN_STRUCTURE",
            DefaultLanguageHighlighterColors.CLASS_NAME
        )
    }
}
