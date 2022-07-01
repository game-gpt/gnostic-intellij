package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.lexer.LexerPositionImpl
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class ValkyrieLexer(val config: ValkyrieLanguageConfig) : Lexer() {
    private var buffer: CharSequence = ""
    private var position = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var currentTokenType: IElementType? = null
    private var endOffset = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.endOffset = endOffset.coerceAtMost(buffer.length)
        this.position = startOffset.coerceIn(0, this.endOffset)
        this.tokenStart = this.position
        this.tokenEnd = this.position
        this.currentTokenType = null
        advance()
        if (currentTokenType == null && tokenStart == tokenEnd) {
            currentTokenType = TokenType.WHITE_SPACE
        }
    }

    override fun getState(): Int = 0

    override fun getTokenType(): IElementType? = currentTokenType

    override fun getTokenStart(): Int = tokenStart

    override fun getTokenEnd(): Int = tokenEnd

    override fun advance() {
        if (position >= endOffset) {
            tokenStart = endOffset
            tokenEnd = endOffset
            currentTokenType = null
            return
        }

        tokenStart = position

        val currentChar = buffer[position]

        when {
            currentChar.isWhitespace() -> {
                while (position < endOffset && buffer[position].isWhitespace()) {
                    position++
                }
                currentTokenType = TokenType.WHITE_SPACE
            }

            currentChar == '#' -> {
                while (position < endOffset && buffer[position] != '\n') {
                    position++
                }
                currentTokenType = ValkyrieTypes.COMMENT
            }

            currentChar == '"' -> {
                position++
                while (position < endOffset && buffer[position] != '"') {
                    position++
                }
                if (position < endOffset) {
                    position++
                }
                currentTokenType = ValkyrieTypes.LITERAL_STRING
            }

            currentChar.isDigit() -> {
                while (position < endOffset && (buffer[position].isDigit() || buffer[position] == '.')) {
                    position++
                }
                currentTokenType = ValkyrieTypes.LITERAL_NUMBER
            }

            currentChar.isLetter() || currentChar == '_' -> {
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_')) {
                    position++
                }
                val identifier = buffer.substring(tokenStart, position)
                currentTokenType = when (identifier) {
                    "async" -> ValkyrieTypes.KEYWORD_ASYNC
                    "await" -> ValkyrieTypes.KEYWORD_AWAIT
                    "break" -> ValkyrieTypes.KEYWORD_BREAK
                    "by" -> ValkyrieTypes.KEYWORD_BY
                    "case" -> ValkyrieTypes.KEYWORD_CASE
                    "class" -> ValkyrieTypes.KEYWORD_CLASS
                    "component" -> ValkyrieTypes.KEYWORD_COMPONENT
                    "compute" -> ValkyrieTypes.KEYWORD_COMPUTE
                    "const" -> ValkyrieTypes.KEYWORD_CONST
                    "constructor" -> ValkyrieTypes.KEYWORD_CONSTRUCTOR
                    "continue" -> ValkyrieTypes.KEYWORD_CONTINUE
                    "else" -> ValkyrieTypes.KEYWORD_ELSE
                    "enum" -> ValkyrieTypes.KEYWORD_ENUM
                    "enums" -> ValkyrieTypes.KEYWORD_ENUMS
                    "event" -> ValkyrieTypes.KEYWORD_EVENT
                    "events" -> ValkyrieTypes.KEYWORD_EVENTS
                    "fallback" -> ValkyrieTypes.KEYWORD_FALLBACK
                    "false" -> ValkyrieTypes.KEYWORD_FALSE
                    "fn" -> ValkyrieTypes.KEYWORD_FN
                    "fragment" -> ValkyrieTypes.KEYWORD_FRAGMENT
                    "if" -> ValkyrieTypes.KEYWORD_IF
                    "in" -> ValkyrieTypes.KEYWORD_IN
                    "let" -> ValkyrieTypes.KEYWORD_LET
                    "loop" -> ValkyrieTypes.KEYWORD_LOOP
                    "macro" -> ValkyrieTypes.KEYWORD_MACRO
                    "match" -> ValkyrieTypes.KEYWORD_MATCH
                    "message" -> ValkyrieTypes.KEYWORD_MESSAGE
                    "mezzo" -> ValkyrieTypes.KEYWORD_MEZZO
                    "micro" -> ValkyrieTypes.KEYWORD_MICRO
                    "model" -> ValkyrieTypes.KEYWORD_MODEL
                    "mut" -> ValkyrieTypes.KEYWORD_MUT
                    "namespace" -> ValkyrieTypes.KEYWORD_NAMESPACE
                    "null" -> ValkyrieTypes.KEYWORD_NULL
                    "render_states" -> ValkyrieTypes.KEYWORD_RENDER_STATES
                    "return" -> ValkyrieTypes.KEYWORD_RETURN
                    "schema" -> if (config.supportSchemaExtension) ValkyrieTypes.KEYWORD_SCHEMA else ValkyrieTypes.IDENTIFIER
                    "service" -> ValkyrieTypes.KEYWORD_SERVICE
                    "shader" -> if (config.supportShaderExtension) ValkyrieTypes.KEYWORD_SHADER else ValkyrieTypes.IDENTIFIER
                    "subscribe" -> ValkyrieTypes.KEYWORD_SUBSCRIBE
                    "system" -> ValkyrieTypes.KEYWORD_SYSTEM
                    "trait" -> ValkyrieTypes.KEYWORD_TRAIT
                    "true" -> ValkyrieTypes.KEYWORD_TRUE
                    "uniforms" -> ValkyrieTypes.KEYWORD_UNIFORMS
                    "until" -> ValkyrieTypes.KEYWORD_UNTIL
                    "using" -> ValkyrieTypes.KEYWORD_USING
                    "vertex" -> ValkyrieTypes.KEYWORD_VERTEX
                    "when" -> ValkyrieTypes.KEYWORD_WHEN
                    "while" -> ValkyrieTypes.KEYWORD_WHILE
                    else -> ValkyrieTypes.IDENTIFIER
                }
            }

            currentChar == ';' -> {
                position++
                currentTokenType = ValkyrieTypes.SEMICOLON
            }

            currentChar == '{' -> {
                position++
                currentTokenType = ValkyrieTypes.LBRACE
            }

            currentChar == '}' -> {
                position++
                currentTokenType = ValkyrieTypes.RBRACE
            }

            currentChar == '(' -> {
                position++
                currentTokenType = ValkyrieTypes.LPAREN
            }

            currentChar == ')' -> {
                position++
                currentTokenType = ValkyrieTypes.RPAREN
            }

            currentChar == '[' -> {
                position++
                currentTokenType = ValkyrieTypes.LBRACK
            }

            currentChar == ']' -> {
                position++
                currentTokenType = ValkyrieTypes.RBRACK
            }

            currentChar == ':' -> {
                position++
                if (position < endOffset && buffer[position] == ':') {
                    position++
                    currentTokenType = ValkyrieTypes.DOUBLE_COLON
                } else {
                    currentTokenType = ValkyrieTypes.COLON
                }
            }

            currentChar == ',' -> {
                position++
                currentTokenType = ValkyrieTypes.COMMA
            }

            currentChar == '.' -> {
                position++
                currentTokenType = ValkyrieTypes.DOT
            }

            currentChar == '+' -> {
                position++
                currentTokenType = ValkyrieTypes.PLUS
            }

            currentChar == '-' -> {
                position++
                if (position < endOffset && buffer[position] == '>') {
                    position++
                    currentTokenType = ValkyrieTypes.ARROW
                } else {
                    currentTokenType = ValkyrieTypes.MINUS
                }
            }

            currentChar == '*' -> {
                position++
                currentTokenType = ValkyrieTypes.MULTIPLY
            }

            currentChar == '/' -> {
                position++
                currentTokenType = ValkyrieTypes.DIVIDE
            }

            currentChar == '=' -> {
                position++
                if (position < endOffset && buffer[position] == '=') {
                    position++
                    currentTokenType = ValkyrieTypes.EQUALITY
                } else if (position < endOffset && buffer[position] == '>') {
                    position++
                    currentTokenType = ValkyrieTypes.FAT_ARROW
                } else {
                    currentTokenType = ValkyrieTypes.EQUALS
                }
            }

            currentChar == '!' -> {
                position++
                if (position < endOffset && buffer[position] == '=') {
                    position++
                    currentTokenType = ValkyrieTypes.NOT_EQUAL
                } else {
                    currentTokenType = ValkyrieTypes.NOT
                }
            }

            currentChar == '<' -> {
                position++
                if (position < endOffset && buffer[position] == '=') {
                    position++
                    currentTokenType = ValkyrieTypes.LESS_EQUAL
                } else {
                    currentTokenType = ValkyrieTypes.LESS
                }
            }

            currentChar == '>' -> {
                position++
                if (position < endOffset && buffer[position] == '=') {
                    position++
                    currentTokenType = ValkyrieTypes.GREATER_EQUAL
                } else {
                    currentTokenType = ValkyrieTypes.GREATER
                }
            }

            currentChar == '&' -> {
                position++
                if (position < endOffset && buffer[position] == '&') {
                    position++
                    currentTokenType = ValkyrieTypes.AND
                } else {
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            currentChar == '|' -> {
                position++
                if (position < endOffset && buffer[position] == '|') {
                    position++
                    currentTokenType = ValkyrieTypes.OR
                } else {
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            currentChar == '%' -> {
                position++
                currentTokenType = ValkyrieTypes.MODULO
            }

            currentChar == '@' -> {
                position++
                currentTokenType = ValkyrieTypes.AT
            }

            currentChar == '?' -> {
                position++
                currentTokenType = ValkyrieTypes.QUESTION
            }

            else -> {
                position++
                currentTokenType = TokenType.BAD_CHARACTER
            }
        }
        position = position.coerceAtMost(endOffset)
        tokenEnd = position
    }

    override fun getCurrentPosition(): LexerPosition {
        return LexerPositionImpl(position, 0)
    }

    override fun restore(position: LexerPosition) {
        this.start(buffer, position.offset, endOffset, 0)
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset

}
