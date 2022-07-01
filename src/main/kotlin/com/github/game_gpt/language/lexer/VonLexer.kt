package com.github.game_gpt.language.lexer

import com.github.game_gpt.language.types.VonTypes
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.lexer.LexerPositionImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.TokenType

class VonLexer : Lexer() {
    private var buffer: CharSequence = ""
    private var position = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var currentTokenType: IElementType? = null
    private var endOffset = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.endOffset = endOffset
        this.position = startOffset.coerceIn(0, endOffset)
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
            currentChar.isDigit() -> {
                while (position < endOffset && (buffer[position].isDigit() || buffer[position] == '.')) {
                    position++
                }
                currentTokenType = VonTypes.LITERAL_NUMBER
            }
            currentChar == '"' -> {
                position++
                while (position < endOffset && buffer[position] != '"') {
                    position++
                }
                if (position < endOffset) {
                    position++
                }
                currentTokenType = VonTypes.LITERAL_STRING
            }
            currentChar == '#' -> {
                while (position < endOffset && buffer[position] != '\n') {
                    position++
                }
                currentTokenType = VonTypes.COMMENT
            }
            currentChar.isLetter() || currentChar == '_' || currentChar == '-' -> {
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_' || buffer[position] == '-')) {
                    position++
                }
                val text = buffer.subSequence(tokenStart, position).toString()
                currentTokenType = when (text) {
                    "true" -> VonTypes.KEYWORD_TRUE
                    "false" -> VonTypes.KEYWORD_FALSE
                    "null" -> VonTypes.KEYWORD_NULL
                    else -> VonTypes.IDENTIFIER
                }
            }
            currentChar == '{' -> {
                position++
                currentTokenType = VonTypes.BRACE_L
            }
            currentChar == '}' -> {
                position++
                currentTokenType = VonTypes.BRACE_R
            }
            currentChar == '[' -> {
                position++
                currentTokenType = VonTypes.BARACK_L
            }
            currentChar == ']' -> {
                position++
                currentTokenType = VonTypes.BARACK_R
            }
            currentChar == ':' -> {
                position++
                currentTokenType = VonTypes.COLON
            }
            currentChar == ',' -> {
                position++
                currentTokenType = VonTypes.COMMA
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