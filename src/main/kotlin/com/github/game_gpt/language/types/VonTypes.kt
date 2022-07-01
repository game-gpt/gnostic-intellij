package com.github.game_gpt.language.types

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object VonTypes {
    // 关键字
    val KEYWORD_TRUE: IElementType = VonTokenType("true")
    val KEYWORD_FALSE: IElementType = VonTokenType("false")
    val KEYWORD_NULL: IElementType = VonTokenType("null")

    // 字面量
    val LITERAL_STRING: IElementType = VonTokenType("LITERAL_STRING")
    val LITERAL_NUMBER: IElementType = VonTokenType("LITERAL_NUMBER")
    val IDENTIFIER: IElementType = VonTokenType("IDENTIFIER")

    // 标点符号
    val BRACE_L: IElementType = VonTokenType("{")
    val BRACE_R: IElementType = VonTokenType("}")
    val BARACK_L: IElementType = VonTokenType("[")
    val BARACK_R: IElementType = VonTokenType("]")
    val COMMA: IElementType = VonTokenType(",")
    val COLON: IElementType = VonTokenType(":")

    // 注释
    val COMMENT: IElementType = VonTokenType("COMMENT")

    // AST 节点类型
    val VON_DICT: IElementType = VonTokenType("VON_DICT")
    val VON_LIST: IElementType = VonTokenType("VON_LIST")
    val VON_PAIR: IElementType = VonTokenType("VON_PAIR")

    // TokenSet
    val KEYWORDS = TokenSet.create(
        KEYWORD_TRUE,
        KEYWORD_FALSE,
        KEYWORD_NULL
    )

    val LITERALS = TokenSet.create(
        LITERAL_STRING,
        LITERAL_NUMBER,
        IDENTIFIER
    )

    val PARENTHESES = TokenSet.create(
        BRACE_L,
        BRACE_R,
        BARACK_L,
        BARACK_R
    )

    val COMMENTS = TokenSet.create(COMMENT)
}
