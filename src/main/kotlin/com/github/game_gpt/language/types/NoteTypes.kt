package com.github.game_gpt.language.types

import com.intellij.psi.tree.TokenSet

/**
 * Notedown 语言的 Token 类型和 AST 节点类型定义
 *
 * 定义了 Notedown 格式（基于 Markdown 的游戏脚本语言）的所有词法单元类型和语法树节点类型。
 * Token 类型用于词法分析阶段，AST 节点类型用于语法分析阶段构建树状结构。
 */
object NoteTypes {

    val COMMENT: NoteTokenType = NoteTokenType("COMMENT")

    val SCENE_MARKER: NoteTokenType = NoteTokenType("SCENE_MARKER")

    val CHOICE_MARKER: NoteTokenType = NoteTokenType("CHOICE_MARKER")

    val ARROW: NoteTokenType = NoteTokenType("ARROW")

    val HORIZONTAL_RULE: NoteTokenType = NoteTokenType("HORIZONTAL_RULE")

    val TILDE: NoteTokenType = NoteTokenType("TILDE")

    val PERCENT: NoteTokenType = NoteTokenType("PERCENT")

    val BRACE_L: NoteTokenType = NoteTokenType("BRACE_L")

    val BRACE_R: NoteTokenType = NoteTokenType("BRACE_R")

    val BRACKET_L: NoteTokenType = NoteTokenType("BRACKET_L")

    val BRACKET_R: NoteTokenType = NoteTokenType("BRACKET_R")

    val PAREN_L: NoteTokenType = NoteTokenType("PAREN_L")

    val PAREN_R: NoteTokenType = NoteTokenType("PAREN_R")

    val COLON: NoteTokenType = NoteTokenType("COLON")

    val DOUBLE_COLON: NoteTokenType = NoteTokenType("DOUBLE_COLON")

    val COMMA: NoteTokenType = NoteTokenType("COMMA")

    val DOT: NoteTokenType = NoteTokenType("DOT")

    val DASH: NoteTokenType = NoteTokenType("DASH")

    val EQUALS: NoteTokenType = NoteTokenType("EQUALS")

    val DOUBLE_EQUALS: NoteTokenType = NoteTokenType("DOUBLE_EQUALS")

    val BANG_EQUALS: NoteTokenType = NoteTokenType("BANG_EQUALS")

    val GT_EQUALS: NoteTokenType = NoteTokenType("GT_EQUALS")

    val LT_EQUALS: NoteTokenType = NoteTokenType("LT_EQUALS")

    val GT: NoteTokenType = NoteTokenType("GT")

    val LT: NoteTokenType = NoteTokenType("LT")

    val AMP_AMP: NoteTokenType = NoteTokenType("AMP_AMP")

    val PIPE_PIPE: NoteTokenType = NoteTokenType("PIPE_PIPE")

    val PLUS_PLUS: NoteTokenType = NoteTokenType("PLUS_PLUS")

    val PLUS_EQUALS: NoteTokenType = NoteTokenType("PLUS_EQUALS")

    val PLUS: NoteTokenType = NoteTokenType("PLUS")

    val BANG: NoteTokenType = NoteTokenType("BANG")

    val MINUS: NoteTokenType = NoteTokenType("MINUS")

    val MINUS_EQUALS: NoteTokenType = NoteTokenType("MINUS_EQUALS")

    val SLASH: NoteTokenType = NoteTokenType("SLASH")

    val PERCENT_CURLY: NoteTokenType = NoteTokenType("PERCENT_CURLY")

    val IDENTIFIER: NoteTokenType = NoteTokenType("IDENTIFIER")

    val STRING: NoteTokenType = NoteTokenType("STRING")

    val NUMBER: NoteTokenType = NoteTokenType("NUMBER")

    val KEYWORD_LET: NoteTokenType = NoteTokenType("KEYWORD_LET")

    val KEYWORD_INCLUDE: NoteTokenType = NoteTokenType("KEYWORD_INCLUDE")

    val KEYWORD_ELSE: NoteTokenType = NoteTokenType("KEYWORD_ELSE")

    val KEYWORD_TRUE: NoteTokenType = NoteTokenType("KEYWORD_TRUE")

    val KEYWORD_FALSE: NoteTokenType = NoteTokenType("KEYWORD_FALSE")

    val KEYWORD_DONE: NoteTokenType = NoteTokenType("KEYWORD_DONE")

    val NEWLINE: NoteTokenType = NoteTokenType("NEWLINE")

    val INDENT: NoteTokenType = NoteTokenType("INDENT")

    val TEXT: NoteTokenType = NoteTokenType("TEXT")

    val STORY_FILE: NoteTokenType = NoteTokenType("STORY_FILE")

    val VARIABLE_DEFINITION: NoteTokenType = NoteTokenType("VARIABLE_DEFINITION")

    val INCLUDE_STATEMENT: NoteTokenType = NoteTokenType("INCLUDE_STATEMENT")

    val SCENE_DEFINITION: NoteTokenType = NoteTokenType("SCENE_DEFINITION")

    val SCENE_HEADER: NoteTokenType = NoteTokenType("SCENE_HEADER")

    val SCENE_BODY: NoteTokenType = NoteTokenType("SCENE_BODY")

    val TEXT_LINE: NoteTokenType = NoteTokenType("TEXT_LINE")

    val DIALOGUE_LINE: NoteTokenType = NoteTokenType("DIALOGUE_LINE")

    val SPEAKER_NAME: NoteTokenType = NoteTokenType("SPEAKER_NAME")

    val DIALOGUE_TEXT: NoteTokenType = NoteTokenType("DIALOGUE_TEXT")

    val CHOICE_BLOCK: NoteTokenType = NoteTokenType("CHOICE_BLOCK")

    val CHOICE_ITEM: NoteTokenType = NoteTokenType("CHOICE_ITEM")

    val CHOICE_TEXT: NoteTokenType = NoteTokenType("CHOICE_TEXT")

    val CHOICE_BODY: NoteTokenType = NoteTokenType("CHOICE_BODY")

    val CONDITION_EXPRESSION: NoteTokenType = NoteTokenType("CONDITION_EXPRESSION")

    val COMMAND_CALL: NoteTokenType = NoteTokenType("COMMAND_CALL")

    val MODULE_NAME: NoteTokenType = NoteTokenType("MODULE_NAME")

    val FUNCTION_NAME: NoteTokenType = NoteTokenType("FUNCTION_NAME")

    val ARGUMENT_LIST: NoteTokenType = NoteTokenType("ARGUMENT_LIST")

    val VARIABLE_OPERATION: NoteTokenType = NoteTokenType("VARIABLE_OPERATION")

    val JUMP_STATEMENT: NoteTokenType = NoteTokenType("JUMP_STATEMENT")

    val JUMP_TARGET: NoteTokenType = NoteTokenType("JUMP_TARGET")

    val CONDITIONAL_BLOCK: NoteTokenType = NoteTokenType("CONDITIONAL_BLOCK")

    val CONDITION_BRANCH: NoteTokenType = NoteTokenType("CONDITION_BRANCH")

    val SEPARATOR: NoteTokenType = NoteTokenType("SEPARATOR")

    val EXPRESSION: NoteTokenType = NoteTokenType("EXPRESSION")

    val KEYWORDS = TokenSet.create(
        KEYWORD_LET,
        KEYWORD_INCLUDE,
        KEYWORD_ELSE,
        KEYWORD_TRUE,
        KEYWORD_FALSE,
        KEYWORD_DONE
    )

    val LITERALS = TokenSet.create(
        STRING,
        NUMBER,
        IDENTIFIER
    )

    val OPERATORS = TokenSet.create(
        EQUALS,
        DOUBLE_EQUALS,
        BANG_EQUALS,
        GT_EQUALS,
        LT_EQUALS,
        GT,
        LT,
        AMP_AMP,
        PIPE_PIPE,
        PLUS_PLUS,
        PLUS_EQUALS,
        PLUS,
        BANG,
        MINUS,
        MINUS_EQUALS,
        SLASH
    )

    val PUNCTUATION = TokenSet.create(
        BRACE_L,
        BRACE_R,
        BRACKET_L,
        BRACKET_R,
        PAREN_L,
        PAREN_R,
        COLON,
        DOUBLE_COLON,
        COMMA,
        DOT,
        DASH,
        ARROW,
        TILDE,
        PERCENT,
        PERCENT_CURLY,
        SCENE_MARKER,
        CHOICE_MARKER,
        HORIZONTAL_RULE
    )

    val COMMENTS = TokenSet.create(COMMENT)

    val SIGNIFICANT_WHITESPACE = TokenSet.create(
        NEWLINE,
        INDENT
    )

    val ALL_TOKENS = TokenSet.create(
        COMMENT, SCENE_MARKER, CHOICE_MARKER, ARROW, HORIZONTAL_RULE,
        TILDE, PERCENT, PERCENT_CURLY,
        BRACE_L, BRACE_R, BRACKET_L, BRACKET_R, PAREN_L, PAREN_R,
        COLON, DOUBLE_COLON, COMMA, DOT, DASH,
        EQUALS, DOUBLE_EQUALS, BANG_EQUALS, GT_EQUALS, LT_EQUALS, GT, LT,
        AMP_AMP, PIPE_PIPE, PLUS_PLUS, PLUS_EQUALS, PLUS, BANG, MINUS, MINUS_EQUALS, SLASH,
        IDENTIFIER, STRING, NUMBER,
        KEYWORD_LET, KEYWORD_INCLUDE, KEYWORD_ELSE, KEYWORD_TRUE, KEYWORD_FALSE, KEYWORD_DONE,
        NEWLINE, INDENT, TEXT
    )
}
