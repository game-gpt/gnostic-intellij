package com.github.game_gpt.language.types

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object VocTypes {
    // Section elements
    val TEMPLATE_SECTION: IElementType = VocElementType("TEMPLATE_SECTION")
    val SCRIPT_SECTION: IElementType = VocElementType("SCRIPT_SECTION")
    val STYLE_SECTION: IElementType = VocElementType("STYLE_SECTION")

    // Template elements
    val WIDGET_ELEMENT: IElementType = VocElementType("WIDGET_ELEMENT")
    val WIDGET_TAG_NAME: IElementType = VocElementType("WIDGET_TAG_NAME")
    val WIDGET_ATTRIBUTE: IElementType = VocElementType("WIDGET_ATTRIBUTE")
    val WIDGET_ATTRIBUTE_NAME: IElementType = VocElementType("WIDGET_ATTRIBUTE_NAME")
    val WIDGET_ATTRIBUTE_VALUE: IElementType = VocElementType("WIDGET_ATTRIBUTE_VALUE")
    val BRACE_EXPRESSION: IElementType = VocElementType("BRACE_EXPRESSION")
    val TEXT_CONTENT: IElementType = VocElementType("TEXT_CONTENT")

    // Script elements
    val USING_DECLARATION: IElementType = VocElementType("USING_DECLARATION")
    val LET_DECLARATION: IElementType = VocElementType("LET_DECLARATION")
    val CONST_DECLARATION: IElementType = VocElementType("CONST_DECLARATION")
    val MICRO_DECLARATION: IElementType = VocElementType("MICRO_DECLARATION")
    val MEZZO_DECLARATION: IElementType = VocElementType("MEZZO_DECLARATION")
    val MACRO_DECLARATION: IElementType = VocElementType("MACRO_DECLARATION")
    val FUNCTION_DECLARATION: IElementType = VocElementType("FUNCTION_DECLARATION")
    val BLOCK: IElementType = VocElementType("BLOCK")
    val PARAMETER_LIST: IElementType = VocElementType("PARAMETER_LIST")
    val EXPRESSION: IElementType = VocElementType("EXPRESSION")
    val EXPRESSION_STATEMENT: IElementType = VocElementType("EXPRESSION_STATEMENT")
    val IDENTIFIER_REFERENCE: IElementType = VocElementType("IDENTIFIER_REFERENCE")

    // Style elements
    val STYLE_RULE: IElementType = VocElementType("STYLE_RULE")
    val STYLE_BLOCK: IElementType = VocElementType("STYLE_BLOCK")
    val STYLE_SELECTOR: IElementType = VocElementType("STYLE_SELECTOR")
    val STYLE_BODY: IElementType = VocElementType("STYLE_BODY")
    val STYLE_PROPERTY: IElementType = VocElementType("STYLE_PROPERTY")
    val STYLE_PROPERTY_NAME: IElementType = VocElementType("STYLE_PROPERTY_NAME")
    val STYLE_PROPERTY_VALUE: IElementType = VocElementType("STYLE_PROPERTY_VALUE")

    // XML tokens
    val TAG_START: IElementType = VocTokenType("TAG_START")
    val TAG_NAME: IElementType = VocTokenType("TAG_NAME")
    val GT: IElementType = VocTokenType("GT")
    val SELF_CLOSE: IElementType = VocTokenType("SELF_CLOSE")
    val XML_END_TAG_START: IElementType = VocTokenType("XML_END_TAG_START")

    // 字面量
    val LITERAL_STRING: IElementType = VocTokenType("LITERAL_STRING")
    val LITERAL_NUMBER: IElementType = VocTokenType("LITERAL_NUMBER")
    val IDENTIFIER: IElementType = VocTokenType("IDENTIFIER")

    // Script 关键词
    val KEYWORD_USING: IElementType = VocTokenType("KEYWORD_USING")
    val KEYWORD_LET: IElementType = VocTokenType("KEYWORD_LET")
    val KEYWORD_CONST: IElementType = VocTokenType("KEYWORD_CONST")
    val KEYWORD_MICRO: IElementType = VocTokenType("KEYWORD_MICRO")
    val KEYWORD_MEZZO: IElementType = VocTokenType("KEYWORD_MEZZO")
    val KEYWORD_MACRO: IElementType = VocTokenType("KEYWORD_MACRO")
    val KEYWORD_FN: IElementType = VocTokenType("KEYWORD_FN")
    val KEYWORD_IF: IElementType = VocTokenType("KEYWORD_IF")
    val KEYWORD_ELSE: IElementType = VocTokenType("KEYWORD_ELSE")
    val KEYWORD_LOOP: IElementType = VocTokenType("KEYWORD_LOOP")
    val KEYWORD_WHILE: IElementType = VocTokenType("KEYWORD_WHILE")
    val KEYWORD_RETURN: IElementType = VocTokenType("KEYWORD_RETURN")
    val KEYWORD_TRUE: IElementType = VocTokenType("KEYWORD_TRUE")
    val KEYWORD_FALSE: IElementType = VocTokenType("KEYWORD_FALSE")
    val KEYWORD_NULL: IElementType = VocTokenType("KEYWORD_NULL")
    val KEYWORD_IN: IElementType = VocTokenType("KEYWORD_IN")

    // Script 运算符
    val ARROW: IElementType = VocTokenType("ARROW")
    val FAT_ARROW: IElementType = VocTokenType("FAT_ARROW")
    val DOUBLE_COLON: IElementType = VocTokenType("DOUBLE_COLON")
    val PLUS: IElementType = VocTokenType("PLUS")
    val MINUS: IElementType = VocTokenType("MINUS")
    val MULTIPLY: IElementType = VocTokenType("MULTIPLY")
    val DIVIDE: IElementType = VocTokenType("DIVIDE")
    val EQUALS: IElementType = VocTokenType("EQUALS")
    val EQUALITY: IElementType = VocTokenType("EQUALITY")
    val NOT_EQUAL: IElementType = VocTokenType("NOT_EQUAL")
    val NOT: IElementType = VocTokenType("NOT")
    val AND: IElementType = VocTokenType("AND")
    val OR: IElementType = VocTokenType("OR")
    val LESS: IElementType = VocTokenType("LESS")
    val GREATER: IElementType = VocTokenType("GREATER")
    val LESS_EQUAL: IElementType = VocTokenType("LESS_EQUAL")
    val GREATER_EQUAL: IElementType = VocTokenType("GREATER_EQUAL")

    // 标点符号
    val LBRACE: IElementType = VocTokenType("{")
    val RBRACE: IElementType = VocTokenType("}")
    val LPAREN: IElementType = VocTokenType("(")
    val RPAREN: IElementType = VocTokenType(")")
    val LBRACK: IElementType = VocTokenType("[")
    val RBRACK: IElementType = VocTokenType("]")
    val SEMICOLON: IElementType = VocTokenType(";")
    val COMMA: IElementType = VocTokenType(",")
    val DOT: IElementType = VocTokenType(".")
    val COLON: IElementType = VocTokenType(":")
    val EQUAL: IElementType = VocTokenType("=")

    // 注释
    val COMMENT: IElementType = VocTokenType("COMMENT")

    // TokenSet
    val SCRIPT_KEYWORDS = TokenSet.create(
        KEYWORD_USING,
        KEYWORD_LET,
        KEYWORD_CONST,
        KEYWORD_MICRO,
        KEYWORD_MEZZO,
        KEYWORD_MACRO,
        KEYWORD_FN,
        KEYWORD_IF,
        KEYWORD_ELSE,
        KEYWORD_LOOP,
        KEYWORD_WHILE,
        KEYWORD_RETURN,
        KEYWORD_TRUE,
        KEYWORD_FALSE,
        KEYWORD_NULL,
        KEYWORD_IN
    )

    val KEYWORDS = TokenSet.create(
        KEYWORD_USING,
        KEYWORD_LET,
        KEYWORD_CONST,
        KEYWORD_MICRO,
        KEYWORD_MEZZO,
        KEYWORD_MACRO,
        KEYWORD_FN,
        KEYWORD_IF,
        KEYWORD_ELSE,
        KEYWORD_LOOP,
        KEYWORD_WHILE,
        KEYWORD_RETURN,
        KEYWORD_TRUE,
        KEYWORD_FALSE,
        KEYWORD_NULL,
        KEYWORD_IN
    )

    val TAG_TOKENS = TokenSet.create(
        TAG_START,
        TAG_NAME,
        GT,
        SELF_CLOSE,
        XML_END_TAG_START
    )

    val LITERALS = TokenSet.create(
        LITERAL_STRING,
        LITERAL_NUMBER,
        IDENTIFIER
    )

    val OPERATORS = TokenSet.create(
        ARROW,
        FAT_ARROW,
        DOUBLE_COLON,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        EQUALS,
        EQUALITY,
        NOT_EQUAL,
        NOT,
        AND,
        OR,
        LESS,
        GREATER,
        LESS_EQUAL,
        GREATER_EQUAL
    )

    val PARENTHESES = TokenSet.create(
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        LBRACK,
        RBRACK
    )

    val COMMENTS = TokenSet.create(COMMENT)

    val DECLARATIONS = TokenSet.create(
        USING_DECLARATION,
        LET_DECLARATION,
        CONST_DECLARATION,
        MICRO_DECLARATION,
        MEZZO_DECLARATION,
        MACRO_DECLARATION,
        FUNCTION_DECLARATION
    )
}
