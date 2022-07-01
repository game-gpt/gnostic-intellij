package com.github.game_gpt.language.lexer

import com.github.game_gpt.language.types.VocTypes
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.lexer.LexerPositionImpl
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class VocLexer : Lexer() {
    private var buffer: CharSequence = ""
    private var position = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var currentTokenType: IElementType? = null
    private var endOffset = 0
    private var state = STATE_INITIAL

    companion object {
        private const val STATE_INITIAL = 0
        private const val STATE_IN_TAG = 1
        private const val STATE_IN_SCRIPT = 2
        private const val STATE_IN_STYLE = 3
        private const val STATE_AFTER_SCRIPT_TAG = 4
        private const val STATE_AFTER_STYLE_TAG = 5
        private const val STATE_IN_CLOSING_TAG = 6
    }

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.endOffset = endOffset
        this.position = startOffset.coerceIn(0, endOffset)
        this.tokenStart = this.position
        this.tokenEnd = this.position
        this.currentTokenType = null
        this.state = initialState
        advance()
    }

    override fun getState(): Int = state

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

        when (state) {
            STATE_IN_SCRIPT -> advanceScriptContent()
            STATE_IN_STYLE -> advanceStyleContent()
            STATE_AFTER_SCRIPT_TAG -> {
                state = STATE_IN_SCRIPT
                advanceDefault()
            }
            STATE_AFTER_STYLE_TAG -> {
                state = STATE_IN_STYLE
                advanceDefault()
            }
            else -> advanceDefault()
        }

        position = position.coerceAtMost(endOffset)
        tokenEnd = position
    }

    private fun advanceDefault() {
        val currentChar = buffer[position]

        when {
            currentChar.isWhitespace() -> {
                while (position < endOffset && buffer[position].isWhitespace()) {
                    position++
                }
                currentTokenType = TokenType.WHITE_SPACE
            }

            currentChar == '<' -> {
                val nextPos = position + 1
                if (nextPos < endOffset) {
                    val nextChar = buffer[nextPos]
                    if (nextChar == '/') {
                        position += 2
                        currentTokenType = VocTypes.XML_END_TAG_START
                        state = STATE_IN_CLOSING_TAG
                    } else if (nextChar == '!' && nextPos + 2 < endOffset && buffer[nextPos + 1] == '-' && buffer[nextPos + 2] == '-') {
                        position += 3
                        while (position < endOffset - 2) {
                            if (buffer[position] == '-' && buffer[position + 1] == '-' && buffer[position + 2] == '>') {
                                position += 3
                                break
                            }
                            position++
                        }
                        currentTokenType = VocTypes.COMMENT
                    } else if (nextChar == '=') {
                        position += 2
                        currentTokenType = VocTypes.LESS_EQUAL
                    } else {
                        position++
                        currentTokenType = VocTypes.TAG_START
                        state = STATE_IN_TAG
                    }
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            currentChar == '>' -> {
                val nextPos = position + 1
                if (nextPos < endOffset && buffer[nextPos] == '=') {
                    position += 2
                    currentTokenType = VocTypes.GREATER_EQUAL
                } else {
                    position++
                    currentTokenType = VocTypes.GT
                    when (state) {
                        STATE_IN_TAG -> state = STATE_INITIAL
                        STATE_IN_CLOSING_TAG -> state = STATE_INITIAL
                        STATE_AFTER_SCRIPT_TAG -> state = STATE_IN_SCRIPT
                        STATE_AFTER_STYLE_TAG -> state = STATE_IN_STYLE
                    }
                }
            }

            currentChar == '/' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '>') {
                    position += 2
                    currentTokenType = VocTypes.SELF_CLOSE
                } else {
                    position++
                    currentTokenType = VocTypes.DIVIDE
                }
            }

            currentChar == '=' -> {
                position++
                currentTokenType = VocTypes.EQUAL
            }

            currentChar == '{' -> {
                position++
                currentTokenType = VocTypes.LBRACE
            }

            currentChar == '}' -> {
                position++
                currentTokenType = VocTypes.RBRACE
            }

            currentChar == '"' -> {
                position++
                while (position < endOffset && buffer[position] != '"') {
                    if (buffer[position] == '\\' && position + 1 < endOffset) {
                        position += 2
                    } else {
                        position++
                    }
                }
                if (position < endOffset) position++
                currentTokenType = VocTypes.LITERAL_STRING
            }

            currentChar.isLetter() || currentChar == '_' || currentChar == '-' || currentChar == ':' || currentChar == '.' -> {
                val start = position
                while (position < endOffset) {
                    val c = buffer[position]
                    if (c.isLetterOrDigit() || c == '_' || c == '-' || c == ':' || c == '.' || c == '[' || c == ']' || c == '#' || c == '/' || c == '(' || c == ')') {
                        position++
                    } else {
                        break
                    }
                }
                val text = buffer.subSequence(start, position).toString()
                if (state == STATE_IN_TAG) {
                    when (text) {
                        "script" -> state = STATE_AFTER_SCRIPT_TAG
                        "style" -> state = STATE_AFTER_STYLE_TAG
                    }
                    currentTokenType = VocTypes.TAG_NAME
                } else if (state == STATE_IN_CLOSING_TAG) {
                    currentTokenType = VocTypes.TAG_NAME
                } else {
                    currentTokenType = VocTypes.IDENTIFIER
                }
            }

            currentChar.isDigit() -> {
                val start = position
                while (position < endOffset && (buffer[position].isDigit() || buffer[position] == '.')) {
                    position++
                }
                currentTokenType = VocTypes.LITERAL_NUMBER
            }

            currentChar == ',' -> {
                position++
                currentTokenType = VocTypes.COMMA
            }

            currentChar == ';' -> {
                position++
                currentTokenType = VocTypes.SEMICOLON
            }

            currentChar == '[' -> {
                position++
                currentTokenType = VocTypes.LBRACK
            }

            currentChar == ']' -> {
                position++
                currentTokenType = VocTypes.RBRACK
            }

            currentChar == '(' -> {
                position++
                currentTokenType = VocTypes.LPAREN
            }

            currentChar == ')' -> {
                position++
                currentTokenType = VocTypes.RPAREN
            }

            currentChar == '+' -> {
                position++
                currentTokenType = VocTypes.PLUS
            }

            currentChar == '-' -> {
                position++
                currentTokenType = VocTypes.MINUS
            }

            currentChar == '*' -> {
                position++
                currentTokenType = VocTypes.MULTIPLY
            }

            currentChar == '%' -> {
                position++
                currentTokenType = TokenType.BAD_CHARACTER
            }

            currentChar == '&' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '&') {
                    position += 2
                    currentTokenType = VocTypes.AND
                } else {
                    position++
                    currentTokenType = VocTypes.IDENTIFIER
                }
            }

            currentChar == '|' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '|') {
                    position += 2
                    currentTokenType = VocTypes.OR
                } else {
                    position++
                    currentTokenType = VocTypes.IDENTIFIER
                }
            }

            currentChar == '!' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '=') {
                    position += 2
                    currentTokenType = VocTypes.NOT_EQUAL
                } else {
                    position++
                    currentTokenType = VocTypes.NOT
                }
            }

            currentChar == '#' -> {
                position++
                currentTokenType = VocTypes.IDENTIFIER
            }

            else -> {
                position++
                currentTokenType = VocTypes.IDENTIFIER
            }
        }
    }

    private fun advanceScriptContent() {
        if (peekString("</script>")) {
            state = STATE_INITIAL
            advanceDefault()
            return
        }

        val currentChar = buffer[position]

        when {
            currentChar.isWhitespace() -> {
                while (position < endOffset && buffer[position].isWhitespace()) {
                    position++
                }
                currentTokenType = TokenType.WHITE_SPACE
            }

            currentChar == '"' -> {
                position++
                while (position < endOffset && buffer[position] != '"') {
                    if (buffer[position] == '\\' && position + 1 < endOffset) {
                        position += 2
                    } else {
                        position++
                    }
                }
                if (position < endOffset) position++
                currentTokenType = VocTypes.LITERAL_STRING
            }

            currentChar == '\'' -> {
                position++
                while (position < endOffset && buffer[position] != '\'') {
                    if (buffer[position] == '\\' && position + 1 < endOffset) {
                        position += 2
                    } else {
                        position++
                    }
                }
                if (position < endOffset) position++
                currentTokenType = VocTypes.LITERAL_STRING
            }

            currentChar.isDigit() -> {
                while (position < endOffset && (buffer[position].isDigit() || buffer[position] == '.')) {
                    position++
                }
                currentTokenType = VocTypes.LITERAL_NUMBER
            }

            currentChar.isLetter() || currentChar == '_' -> {
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_')) {
                    position++
                }
                val text = buffer.subSequence(tokenStart, position).toString()
                currentTokenType = when (text) {
                    "using" -> VocTypes.KEYWORD_USING
                    "let" -> VocTypes.KEYWORD_LET
                    "const" -> VocTypes.KEYWORD_CONST
                    "micro" -> VocTypes.KEYWORD_MICRO
                    "mezzo" -> VocTypes.KEYWORD_MEZZO
                    "macro" -> VocTypes.KEYWORD_MACRO
                    "fn" -> VocTypes.KEYWORD_FN
                    "if" -> VocTypes.KEYWORD_IF
                    "else" -> VocTypes.KEYWORD_ELSE
                    "loop" -> VocTypes.KEYWORD_LOOP
                    "while" -> VocTypes.KEYWORD_WHILE
                    "return" -> VocTypes.KEYWORD_RETURN
                    "true" -> VocTypes.KEYWORD_TRUE
                    "false" -> VocTypes.KEYWORD_FALSE
                    "null" -> VocTypes.KEYWORD_NULL
                    "in" -> VocTypes.KEYWORD_IN
                    else -> VocTypes.IDENTIFIER
                }
            }

            currentChar == '#' -> {
                position++
                while (position < endOffset && buffer[position] != '\n') {
                    position++
                }
                currentTokenType = VocTypes.COMMENT
            }

            currentChar == ':' -> {
                if (position + 1 < endOffset && buffer[position + 1] == ':') {
                    position += 2
                    currentTokenType = VocTypes.DOUBLE_COLON
                } else {
                    position++
                    currentTokenType = VocTypes.COLON
                }
            }

            currentChar == '-' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '>') {
                    position += 2
                    currentTokenType = VocTypes.ARROW
                } else {
                    position++
                    currentTokenType = VocTypes.MINUS
                }
            }

            currentChar == '=' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '=') {
                    position += 2
                    currentTokenType = VocTypes.EQUALITY
                } else if (position + 1 < endOffset && buffer[position + 1] == '>') {
                    position += 2
                    currentTokenType = VocTypes.FAT_ARROW
                } else {
                    position++
                    currentTokenType = VocTypes.EQUALS
                }
            }

            currentChar == '!' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '=') {
                    position += 2
                    currentTokenType = VocTypes.NOT_EQUAL
                } else {
                    position++
                    currentTokenType = VocTypes.NOT
                }
            }

            currentChar == '<' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '=') {
                    position += 2
                    currentTokenType = VocTypes.LESS_EQUAL
                } else {
                    position++
                    currentTokenType = VocTypes.LESS
                }
            }

            currentChar == '>' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '=') {
                    position += 2
                    currentTokenType = VocTypes.GREATER_EQUAL
                } else {
                    position++
                    currentTokenType = VocTypes.GREATER
                }
            }

            currentChar == '&' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '&') {
                    position += 2
                    currentTokenType = VocTypes.AND
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            currentChar == '|' -> {
                if (position + 1 < endOffset && buffer[position + 1] == '|') {
                    position += 2
                    currentTokenType = VocTypes.OR
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            currentChar == '+' -> {
                position++
                currentTokenType = VocTypes.PLUS
            }

            currentChar == '*' -> {
                position++
                currentTokenType = VocTypes.MULTIPLY
            }

            currentChar == '/' -> {
                position++
                currentTokenType = VocTypes.DIVIDE
            }

            currentChar == '%' -> {
                position++
                currentTokenType = TokenType.BAD_CHARACTER
            }

            currentChar == '{' -> {
                position++
                currentTokenType = VocTypes.LBRACE
            }

            currentChar == '}' -> {
                position++
                currentTokenType = VocTypes.RBRACE
            }

            currentChar == '(' -> {
                position++
                currentTokenType = VocTypes.LPAREN
            }

            currentChar == ')' -> {
                position++
                currentTokenType = VocTypes.RPAREN
            }

            currentChar == '[' -> {
                position++
                currentTokenType = VocTypes.LBRACK
            }

            currentChar == ']' -> {
                position++
                currentTokenType = VocTypes.RBRACK
            }

            currentChar == ';' -> {
                position++
                currentTokenType = VocTypes.SEMICOLON
            }

            currentChar == ',' -> {
                position++
                currentTokenType = VocTypes.COMMA
            }

            currentChar == '.' -> {
                position++
                currentTokenType = VocTypes.DOT
            }

            else -> {
                position++
                currentTokenType = TokenType.BAD_CHARACTER
            }
        }
    }

    private fun advanceStyleContent() {
        if (peekString("</style>")) {
            state = STATE_INITIAL
            advanceDefault()
            return
        }

        val currentChar = buffer[position]

        when {
            currentChar.isWhitespace() -> {
                while (position < endOffset && buffer[position].isWhitespace()) {
                    position++
                }
                currentTokenType = TokenType.WHITE_SPACE
            }

            currentChar == '{' -> {
                position++
                currentTokenType = VocTypes.LBRACE
            }

            currentChar == '}' -> {
                position++
                currentTokenType = VocTypes.RBRACE
            }

            currentChar == ':' -> {
                position++
                currentTokenType = VocTypes.COLON
            }

            currentChar == ';' -> {
                position++
                currentTokenType = VocTypes.SEMICOLON
            }

            currentChar == '.' -> {
                position++
                if (position < endOffset && buffer[position] == '-') {
                    while (position < endOffset && !buffer[position].isWhitespace() && buffer[position] != '{') {
                        position++
                    }
                } else if (position < endOffset && (buffer[position].isLetter() || buffer[position] == '_' || buffer[position].isDigit())) {
                    while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_' || buffer[position] == '-')) {
                        position++
                    }
                }
                currentTokenType = VocTypes.IDENTIFIER
            }

            currentChar == '$' -> {
                position++
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_' || buffer[position] == '-')) {
                    position++
                }
                currentTokenType = VocTypes.IDENTIFIER
            }

            currentChar == '#' -> {
                position++
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '-')) {
                    position++
                }
                currentTokenType = VocTypes.LITERAL_STRING
            }

            currentChar == '&' -> {
                position++
                currentTokenType = VocTypes.IDENTIFIER
            }

            currentChar == '[' -> {
                position++
                while (position < endOffset && buffer[position] != ']') {
                    position++
                }
                if (position < endOffset) position++
                currentTokenType = VocTypes.IDENTIFIER
            }

            currentChar.isLetter() || currentChar == '_' || currentChar == '-' -> {
                while (position < endOffset && (buffer[position].isLetterOrDigit() || buffer[position] == '_' || buffer[position] == '-')) {
                    position++
                }
                currentTokenType = VocTypes.IDENTIFIER
            }

            currentChar.isDigit() -> {
                while (position < endOffset && (buffer[position].isDigit() || buffer[position] == '%')) {
                    position++
                }
                currentTokenType = VocTypes.LITERAL_NUMBER
            }

            else -> {
                position++
                currentTokenType = VocTypes.IDENTIFIER
            }
        }
    }

    private fun peekString(s: String): Boolean {
        if (position + s.length > endOffset) return false
        for (i in s.indices) {
            if (buffer[position + i] != s[i]) return false
        }
        return true
    }

    override fun getCurrentPosition(): LexerPosition {
        return LexerPositionImpl(position, state)
    }

    override fun restore(position: LexerPosition) {
        this.position = position.offset
        this.state = position.state
        this.tokenStart = this.position
        this.tokenEnd = this.position
        this.currentTokenType = null
        advance()
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset
}
