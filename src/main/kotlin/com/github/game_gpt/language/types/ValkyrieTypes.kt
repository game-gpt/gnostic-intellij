package com.github.game_gpt.language.types

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object ValkyrieTypes {
    val NAMESPACE_DECLARATION: IElementType = ValkyrieElementType("NAMESPACE_DECLARATION")
    val USING_DECLARATION: IElementType = ValkyrieElementType("USING_DECLARATION")
    val CLASS_DECLARATION: IElementType = ValkyrieElementType("CLASS_DECLARATION")
    val SHADER_DECLARATION: IElementType = ValkyrieElementType("SHADER_DECLARATION")
    val TRAIT_DECLARATION: IElementType = ValkyrieElementType("TRAIT_DECLARATION")
    val MICRO_DECLARATION: IElementType = ValkyrieElementType("MICRO_DECLARATION")
    val MEZZO_DECLARATION: IElementType = ValkyrieElementType("MEZZO_DECLARATION")
    val MACRO_DECLARATION: IElementType = ValkyrieElementType("MACRO_DECLARATION")
    val LET_DECLARATION: IElementType = ValkyrieElementType("LET_DECLARATION")
    val CONST_DECLARATION: IElementType = ValkyrieElementType("CONST_DECLARATION")
    val FUNCTION_DECLARATION: IElementType = ValkyrieElementType("FUNCTION_DECLARATION")
    val ENUMS_DECLARATION: IElementType = ValkyrieElementType("ENUMS_DECLARATION")
    val ENUM_DECLARATION: IElementType = ValkyrieElementType("ENUM_DECLARATION")
    val SCHEMA_DECLARATION: IElementType = ValkyrieElementType("SCHEMA_DECLARATION")
    val MODEL_DECLARATION: IElementType = ValkyrieElementType("MODEL_DECLARATION")
    val SERVICE_DECLARATION: IElementType = ValkyrieElementType("SERVICE_DECLARATION")
    val MESSAGE_DECLARATION: IElementType = ValkyrieElementType("MESSAGE_DECLARATION")
    val SHADER_KIND: IElementType = ValkyrieElementType("SHADER_KIND")
    val SHADER_PROPERTY: IElementType = ValkyrieElementType("SHADER_PROPERTY")
    val RENDER_STATES_BLOCK: IElementType = ValkyrieElementType("RENDER_STATES_BLOCK")
    val VERTEX_FUNCTION: IElementType = ValkyrieElementType("VERTEX_FUNCTION")
    val FRAGMENT_FUNCTION: IElementType = ValkyrieElementType("FRAGMENT_FUNCTION")
    val COMPUTE_FUNCTION: IElementType = ValkyrieElementType("COMPUTE_FUNCTION")
    val UNIFORMS_BLOCK: IElementType = ValkyrieElementType("UNIFORMS_BLOCK")
    val FALLBACK_BLOCK: IElementType = ValkyrieElementType("FALLBACK_BLOCK")
    val UNIFORM_FIELD: IElementType = ValkyrieElementType("UNIFORM_FIELD")
    val SCHEMA_CONFIG_FIELD: IElementType = ValkyrieElementType("SCHEMA_CONFIG_FIELD")
    val MODEL_FIELD: IElementType = ValkyrieElementType("MODEL_FIELD")
    val SERVICE_METHOD: IElementType = ValkyrieElementType("SERVICE_METHOD")
    val MESSAGE_FIELD: IElementType = ValkyrieElementType("MESSAGE_FIELD")
    val ENUM_VARIANT: IElementType = ValkyrieElementType("ENUM_VARIANT")
    val ANNOTATION: IElementType = ValkyrieElementType("ANNOTATION")
    val FIELD_DECLARATION: IElementType = ValkyrieElementType("FIELD_DECLARATION")
    val TYPE_REFERENCE: IElementType = ValkyrieElementType("TYPE_REFERENCE")
    val BLOCK: IElementType = ValkyrieElementType("BLOCK")
    val PARAMETER_LIST: IElementType = ValkyrieElementType("PARAMETER_LIST")
    val ARGUMENT_LIST: IElementType = ValkyrieElementType("ARGUMENT_LIST")
    val STATEMENT: IElementType = ValkyrieElementType("STATEMENT")
    val EXPRESSION: IElementType = ValkyrieElementType("EXPRESSION")
    val IF_STATEMENT: IElementType = ValkyrieElementType("IF_STATEMENT")
    val LOOP_STATEMENT: IElementType = ValkyrieElementType("LOOP_STATEMENT")
    val WHILE_STATEMENT: IElementType = ValkyrieElementType("WHILE_STATEMENT")
    val RETURN_STATEMENT: IElementType = ValkyrieElementType("RETURN_STATEMENT")
    val IDENTIFIER_REFERENCE: IElementType = ValkyrieElementType("IDENTIFIER_REFERENCE")

    val KEYWORD_NAMESPACE: IElementType = ValkyrieTokenType("namespace")
    val KEYWORD_USING: IElementType = ValkyrieTokenType("using")
    val KEYWORD_MICRO: IElementType = ValkyrieTokenType("micro")
    val KEYWORD_MEZZO: IElementType = ValkyrieTokenType("mezzo")
    val KEYWORD_MACRO: IElementType = ValkyrieTokenType("macro")

    val KEYWORD_LET: IElementType = ValkyrieTokenType("let")
    val KEYWORD_CLASS: IElementType = ValkyrieTokenType("class")
    val KEYWORD_CONST: IElementType = ValkyrieTokenType("const")
    val KEYWORD_FN: IElementType = ValkyrieTokenType("fn")
    val KEYWORD_IF: IElementType = ValkyrieTokenType("if")
    val KEYWORD_ELSE: IElementType = ValkyrieTokenType("else")
    val KEYWORD_LOOP: IElementType = ValkyrieTokenType("loop")
    val KEYWORD_WHILE: IElementType = ValkyrieTokenType("while")
    val KEYWORD_RETURN: IElementType = ValkyrieTokenType("return")
    val KEYWORD_TRUE: IElementType = ValkyrieTokenType("true")
    val KEYWORD_FALSE: IElementType = ValkyrieTokenType("false")
    val KEYWORD_NULL: IElementType = ValkyrieTokenType("null")
    val KEYWORD_IN: IElementType = ValkyrieTokenType("in")
    val KEYWORD_UNTIL: IElementType = ValkyrieTokenType("until")
    val KEYWORD_BREAK: IElementType = ValkyrieTokenType("break")
    val KEYWORD_CONTINUE: IElementType = ValkyrieTokenType("continue")
    val KEYWORD_SHADER: IElementType = ValkyrieTokenType("shader")
    val KEYWORD_TRAIT: IElementType = ValkyrieTokenType("trait")
    val KEYWORD_SCHEMA: IElementType = ValkyrieTokenType("schema")
    val KEYWORD_ENUM: IElementType = ValkyrieTokenType("enum")
    val KEYWORD_ENUMS: IElementType = ValkyrieTokenType("enums")
    val KEYWORD_MODEL: IElementType = ValkyrieTokenType("model")
    val KEYWORD_SERVICE: IElementType = ValkyrieTokenType("service")
    val KEYWORD_MESSAGE: IElementType = ValkyrieTokenType("message")
    val KEYWORD_BY: IElementType = ValkyrieTokenType("by")
    val KEYWORD_RENDER_STATES: IElementType = ValkyrieTokenType("render_states")
    val KEYWORD_VERTEX: IElementType = ValkyrieTokenType("vertex")
    val KEYWORD_FRAGMENT: IElementType = ValkyrieTokenType("fragment")
    val KEYWORD_COMPUTE: IElementType = ValkyrieTokenType("compute")
    val KEYWORD_UNIFORMS: IElementType = ValkyrieTokenType("uniforms")
    val KEYWORD_FALLBACK: IElementType = ValkyrieTokenType("fallback")
    val KEYWORD_WHEN: IElementType = ValkyrieTokenType("when")
    val KEYWORD_CONSTRUCTOR: IElementType = ValkyrieTokenType("constructor")
    val KEYWORD_COMPONENT: IElementType = ValkyrieTokenType("component")
    val KEYWORD_SYSTEM: IElementType = ValkyrieTokenType("system")
    val KEYWORD_EVENTS: IElementType = ValkyrieTokenType("events")
    val KEYWORD_EVENT: IElementType = ValkyrieTokenType("event")
    val KEYWORD_SUBSCRIBE: IElementType = ValkyrieTokenType("subscribe")
    val KEYWORD_ASYNC: IElementType = ValkyrieTokenType("async")
    val KEYWORD_AWAIT: IElementType = ValkyrieTokenType("await")
    val KEYWORD_MATCH: IElementType = ValkyrieTokenType("match")
    val KEYWORD_CASE: IElementType = ValkyrieTokenType("case")
    val KEYWORD_MUT: IElementType = ValkyrieTokenType("mut")
    val AT: IElementType = ValkyrieTokenType("AT")
    val QUESTION: IElementType = ValkyrieTokenType("QUESTION")

    val LITERAL_STRING: IElementType = ValkyrieTokenType("LITERAL_STRING")
    val LITERAL_NUMBER: IElementType = ValkyrieTokenType("LITERAL_NUMBER")
    val IDENTIFIER: IElementType = ValkyrieTokenType("IDENTIFIER")

    val PLUS: IElementType = ValkyrieTokenType("PLUS")
    val MINUS: IElementType = ValkyrieTokenType("MINUS")
    val MULTIPLY: IElementType = ValkyrieTokenType("MULTIPLY")
    val DIVIDE: IElementType = ValkyrieTokenType("DIVIDE")
    val MODULO: IElementType = ValkyrieTokenType("MODULO")
    val EQUALS: IElementType = ValkyrieTokenType("EQUALS")
    val EQUALITY: IElementType = ValkyrieTokenType("EQUALITY")
    val NOT_EQUAL: IElementType = ValkyrieTokenType("NOT_EQUAL")
    val GREATER: IElementType = ValkyrieTokenType("GREATER")
    val GREATER_EQUAL: IElementType = ValkyrieTokenType("GREATER_EQUAL")
    val LESS: IElementType = ValkyrieTokenType("LESS")
    val LESS_EQUAL: IElementType = ValkyrieTokenType("LESS_EQUAL")
    val AND: IElementType = ValkyrieTokenType("AND")
    val OR: IElementType = ValkyrieTokenType("OR")
    val NOT: IElementType = ValkyrieTokenType("NOT")
    val ARROW: IElementType = ValkyrieTokenType("ARROW")
    val FAT_ARROW: IElementType = ValkyrieTokenType("FAT_ARROW")
    val DOUBLE_COLON: IElementType = ValkyrieTokenType("DOUBLE_COLON")

    val LBRACE: IElementType = ValkyrieTokenType("LBRACE")
    val RBRACE: IElementType = ValkyrieTokenType("RBRACE")
    val LPAREN: IElementType = ValkyrieTokenType("LPAREN")
    val RPAREN: IElementType = ValkyrieTokenType("RPAREN")
    val LBRACK: IElementType = ValkyrieTokenType("LBRACK")
    val RBRACK: IElementType = ValkyrieTokenType("RBRACK")
    val SEMICOLON: IElementType = ValkyrieTokenType("SEMICOLON")
    val COMMA: IElementType = ValkyrieTokenType("COMMA")
    val DOT: IElementType = ValkyrieTokenType("DOT")
    val COLON: IElementType = ValkyrieTokenType("COLON")

    val COMMENT: IElementType = ValkyrieTokenType("COMMENT")

    val KEYWORDS = TokenSet.create(
        KEYWORD_NAMESPACE,
        KEYWORD_USING,
        KEYWORD_MICRO,
        KEYWORD_MEZZO,
        KEYWORD_MACRO,
        KEYWORD_LET,
        KEYWORD_CLASS,
        KEYWORD_CONST,
        KEYWORD_FN,
        KEYWORD_IF,
        KEYWORD_ELSE,
        KEYWORD_LOOP,
        KEYWORD_WHILE,
        KEYWORD_RETURN,
        KEYWORD_TRUE,
        KEYWORD_FALSE,
        KEYWORD_NULL,
        KEYWORD_IN,
        KEYWORD_UNTIL,
        KEYWORD_BREAK,
        KEYWORD_CONTINUE,
        KEYWORD_SHADER,
        KEYWORD_TRAIT,
        KEYWORD_SCHEMA,
        KEYWORD_ENUM,
        KEYWORD_ENUMS,
        KEYWORD_MODEL,
        KEYWORD_SERVICE,
        KEYWORD_MESSAGE,
        KEYWORD_BY,
        KEYWORD_RENDER_STATES,
        KEYWORD_VERTEX,
        KEYWORD_FRAGMENT,
        KEYWORD_COMPUTE,
        KEYWORD_UNIFORMS,
        KEYWORD_FALLBACK,
        KEYWORD_WHEN,
        KEYWORD_CONSTRUCTOR,
        KEYWORD_COMPONENT,
        KEYWORD_SYSTEM,
        KEYWORD_EVENTS,
        KEYWORD_EVENT,
        KEYWORD_SUBSCRIBE,
        KEYWORD_ASYNC,
        KEYWORD_AWAIT,
        KEYWORD_MATCH,
        KEYWORD_CASE,
        KEYWORD_MUT
    )

    val LITERALS = TokenSet.create(
        LITERAL_STRING,
        LITERAL_NUMBER,
        IDENTIFIER
    )

    val OPERATORS = TokenSet.create(
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        MODULO,
        EQUALS,
        EQUALITY,
        NOT_EQUAL,
        GREATER,
        GREATER_EQUAL,
        LESS,
        LESS_EQUAL,
        AND,
        OR,
        NOT,
        ARROW,
        FAT_ARROW,
        DOUBLE_COLON
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
        NAMESPACE_DECLARATION,
        USING_DECLARATION,
        CLASS_DECLARATION,
        SHADER_DECLARATION,
        TRAIT_DECLARATION,
        MICRO_DECLARATION,
        MEZZO_DECLARATION,
        MACRO_DECLARATION,
        LET_DECLARATION,
        CONST_DECLARATION,
        FUNCTION_DECLARATION,
        ENUMS_DECLARATION,
        ENUM_DECLARATION,
        SCHEMA_DECLARATION,
        MODEL_DECLARATION,
        SERVICE_DECLARATION,
        MESSAGE_DECLARATION,
        SHADER_KIND,
        SHADER_PROPERTY,
        RENDER_STATES_BLOCK,
        VERTEX_FUNCTION,
        FRAGMENT_FUNCTION,
        COMPUTE_FUNCTION,
        UNIFORMS_BLOCK,
        FALLBACK_BLOCK,
        UNIFORM_FIELD,
        SCHEMA_CONFIG_FIELD,
        MODEL_FIELD,
        SERVICE_METHOD,
        MESSAGE_FIELD,
        ENUM_VARIANT,
        ANNOTATION,
        FIELD_DECLARATION,
        TYPE_REFERENCE
    )
}
