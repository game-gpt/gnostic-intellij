package com.github.game_gpt.language.lexer

import com.github.game_gpt.ide.config.NotedownLanguageConfig
import com.github.game_gpt.language.types.NoteTypes
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.lexer.LexerPositionImpl
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

/**
 * Notedown 语言的词法分析器
 *
 * 实现基于行的状态化词法分析，支持 Notedown 格式的所有语法元素。
 * 词法分析器维护行首状态，用于区分行首标记和行内相同字符。
 * 支持缩进感知、换行感知和中文标识符。
 */
class NoteLexer(val config: NotedownLanguageConfig) : Lexer() {

    private var buffer: CharSequence = ""
    private var position = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var currentTokenType: IElementType? = null
    private var endOffset = 0
    private var atLineStart = true

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.endOffset = endOffset
        this.position = startOffset.coerceIn(0, endOffset)
        this.tokenStart = this.position
        this.tokenEnd = this.position
        this.currentTokenType = null
        this.atLineStart = true
        advance()
    }

    override fun getState(): Int = if (atLineStart) STATE_LINE_START else STATE_INLINE

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

        if (atLineStart) {
            parseLineStart()
        } else {
            parseInline()
        }

        position = position.coerceAtMost(endOffset)
        tokenEnd = position
    }

    /**
     * 解析行首位置的内容
     *
     * 行首位置需要识别缩进和行首标记。
     */
    private fun parseLineStart() {
        if (position >= endOffset) {
            currentTokenType = null
            return
        }

        val indentStart = position
        while (position < endOffset && (buffer[position] == ' ' || buffer[position] == '\t')) {
            position++
        }

        if (position > indentStart) {
            atLineStart = false
            currentTokenType = NoteTypes.INDENT
            return
        }

        atLineStart = false
        parseLinePrefix()
    }

    /**
     * 解析行首前缀标记
     *
     * 识别行首的特殊标记，如 #、%、*、==、->、~、{、-、--- 等。
     */
    private fun parseLinePrefix() {
        if (position >= endOffset) {
            currentTokenType = null
            return
        }

        val ch = buffer[position]

        when {
            ch == '\n' -> {
                position++
                atLineStart = true
                currentTokenType = NoteTypes.NEWLINE
            }

            ch == '\r' -> {
                position++
                if (position < endOffset && buffer[position] == '\n') {
                    position++
                }
                atLineStart = true
                currentTokenType = NoteTypes.NEWLINE
            }

            ch == '#' -> {
                while (position < endOffset && buffer[position] != '\n' && buffer[position] != '\r') {
                    position++
                }
                currentTokenType = NoteTypes.COMMENT
            }

            ch == '=' && lookaheadChar(1) == '=' -> {
                position += 2
                currentTokenType = NoteTypes.SCENE_MARKER
            }

            ch == '-' -> {
                if (lookaheadChar(1) == '-' && lookaheadChar(2) == '-') {
                    position += 3
                    currentTokenType = NoteTypes.HORIZONTAL_RULE
                } else if (lookaheadChar(1) == '>') {
                    position += 2
                    currentTokenType = NoteTypes.ARROW
                } else {
                    position++
                    currentTokenType = NoteTypes.DASH
                }
            }

            ch == '*' -> {
                position++
                currentTokenType = NoteTypes.CHOICE_MARKER
            }

            ch == '%' -> {
                position++
                currentTokenType = NoteTypes.PERCENT
            }

            ch == '~' -> {
                position++
                currentTokenType = NoteTypes.TILDE
            }

            ch == '{' -> {
                position++
                currentTokenType = NoteTypes.BRACE_L
            }

            else -> parseInline()
        }
    }

    /**
     * 解析行内位置的内容
     *
     * 识别行内的各种 token，如标识符、字符串、数字、运算符等。
     */
    private fun parseInline() {
        if (position >= endOffset) {
            currentTokenType = null
            return
        }

        val ch = buffer[position]

        when {
            ch == '\n' -> {
                position++
                atLineStart = true
                currentTokenType = NoteTypes.NEWLINE
            }

            ch == '\r' -> {
                position++
                if (position < endOffset && buffer[position] == '\n') {
                    position++
                }
                atLineStart = true
                currentTokenType = NoteTypes.NEWLINE
            }

            ch.isWhitespace() -> {
                while (position < endOffset && buffer[position] != '\n' && buffer[position] != '\r' && buffer[position].isWhitespace()) {
                    position++
                }
                currentTokenType = TokenType.WHITE_SPACE
            }

            ch == '"' -> {
                parseString()
            }

            ch.isDigit() -> {
                parseNumber()
            }

            isIdentifierStart(ch) -> {
                parseIdentifierOrKeyword()
            }

            ch == '=' -> {
                if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.DOUBLE_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.EQUALS
                }
            }

            ch == '!' -> {
                if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.BANG_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.BANG
                }
            }

            ch == '>' -> {
                if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.GT_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.GT
                }
            }

            ch == '<' -> {
                if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.LT_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.LT
                }
            }

            ch == '&' -> {
                if (lookaheadChar(1) == '&') {
                    position += 2
                    currentTokenType = NoteTypes.AMP_AMP
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            ch == '|' -> {
                if (lookaheadChar(1) == '|') {
                    position += 2
                    currentTokenType = NoteTypes.PIPE_PIPE
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }

            ch == '+' -> {
                if (lookaheadChar(1) == '+') {
                    position += 2
                    currentTokenType = NoteTypes.PLUS_PLUS
                } else if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.PLUS_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.PLUS
                }
            }

            ch == '-' -> {
                if (lookaheadChar(1) == '>') {
                    position += 2
                    currentTokenType = NoteTypes.ARROW
                } else if (lookaheadChar(1) == '=') {
                    position += 2
                    currentTokenType = NoteTypes.MINUS_EQUALS
                } else {
                    position++
                    currentTokenType = NoteTypes.MINUS
                }
            }

            ch == ':' -> {
                if (lookaheadChar(1) == ':') {
                    position += 2
                    currentTokenType = NoteTypes.DOUBLE_COLON
                } else {
                    position++
                    currentTokenType = NoteTypes.COLON
                }
            }

            ch == '\uFF1A' -> {
                position++
                currentTokenType = NoteTypes.COLON
            }

            ch == '{' -> {
                position++
                currentTokenType = NoteTypes.BRACE_L
            }

            ch == '}' -> {
                position++
                currentTokenType = NoteTypes.BRACE_R
            }

            ch == '[' -> {
                position++
                currentTokenType = NoteTypes.BRACKET_L
            }

            ch == ']' -> {
                position++
                currentTokenType = NoteTypes.BRACKET_R
            }

            ch == '(' -> {
                position++
                currentTokenType = NoteTypes.PAREN_L
            }

            ch == ')' -> {
                position++
                currentTokenType = NoteTypes.PAREN_R
            }

            ch == ',' -> {
                position++
                currentTokenType = NoteTypes.COMMA
            }

            ch == '.' -> {
                position++
                currentTokenType = NoteTypes.DOT
            }

            ch == '/' -> {
                position++
                currentTokenType = NoteTypes.SLASH
            }

            ch == '%' -> {
                if (lookaheadChar(1) == '{') {
                    position += 2
                    currentTokenType = NoteTypes.PERCENT_CURLY
                } else {
                    position++
                    currentTokenType = NoteTypes.PERCENT
                }
            }

            ch == '~' -> {
                position++
                currentTokenType = NoteTypes.TILDE
            }

            ch == '*' -> {
                position++
                currentTokenType = NoteTypes.CHOICE_MARKER
            }

            ch == '=' -> {
                position++
                currentTokenType = NoteTypes.EQUALS
            }

            ch == '_' -> {
                parseIdentifierOrKeyword()
            }

            else -> {
                if (isCJKChar(ch) || isChinesePunctuation(ch)) {
                    parseText()
                } else {
                    position++
                    currentTokenType = TokenType.BAD_CHARACTER
                }
            }
        }
    }

    /**
     * 解析字符串字面量
     *
     * 识别双引号包围的字符串内容。
     */
    private fun parseString() {
        position++
        while (position < endOffset && buffer[position] != '"' && buffer[position] != '\n' && buffer[position] != '\r') {
            if (buffer[position] == '\\' && position + 1 < endOffset) {
                position += 2
            } else {
                position++
            }
        }
        if (position < endOffset && buffer[position] == '"') {
            position++
        }
        currentTokenType = NoteTypes.STRING
    }

    /**
     * 解析数字字面量
     *
     * 识别整数和浮点数。
     */
    private fun parseNumber() {
        while (position < endOffset && buffer[position].isDigit()) {
            position++
        }
        if (position < endOffset && buffer[position] == '.' && position + 1 < endOffset && buffer[position + 1].isDigit()) {
            position++
            while (position < endOffset && buffer[position].isDigit()) {
                position++
            }
        }
        currentTokenType = NoteTypes.NUMBER
    }

    /**
     * 解析标识符或关键字
     *
     * 识别以字母、下划线或中文字符开头的标识符，并判断是否为关键字。
     */
    private fun parseIdentifierOrKeyword() {
        while (position < endOffset && isIdentifierPart(buffer[position])) {
            position++
        }
        val text = buffer.subSequence(tokenStart, position).toString()
        currentTokenType = when (text) {
            "let" -> NoteTypes.KEYWORD_LET
            "include" -> NoteTypes.KEYWORD_INCLUDE
            "else" -> NoteTypes.KEYWORD_ELSE
            "true" -> NoteTypes.KEYWORD_TRUE
            "false" -> NoteTypes.KEYWORD_FALSE
            "DONE" -> NoteTypes.KEYWORD_DONE
            else -> NoteTypes.IDENTIFIER
        }
    }

    /**
     * 解析普通文本内容
     *
     * 识别包含中文字符和中文标点的普通文本，直到遇到特殊字符或换行符。
     * 中文标点（，。！？、；""''）作为文本内容的一部分。
     * 全角冒号（：）单独识别为 COLON token。
     */
    private fun parseText() {
        while (position < endOffset) {
            val ch = buffer[position]
            if (ch == '\n' || ch == '\r') break
            if (ch == ':' || ch == '{' || ch == '}' || ch == '[' || ch == ']' ||
                ch == '(' || ch == ')' || ch == '"' || ch == '=' || ch == '!' ||
                ch == '<' || ch == '>' || ch == '&' || ch == '|' || ch == '+' ||
                ch == '-' || ch == ',' || ch == '.' || ch == '~' || ch == '%' ||
                ch == '*' || ch == '/'
            ) break
            if (ch == '\uFF1A') break
            if (ch.isWhitespace() && position + 1 < endOffset) {
                val nextNonSpace = peekNextNonSpace(position)
                if ((nextNonSpace == ':' || nextNonSpace == '\uFF1A') && isLikelyDialogue(position)) break
            }
            position++
        }
        if (position == tokenStart) {
            position++
            currentTokenType = TokenType.BAD_CHARACTER
        } else {
            currentTokenType = NoteTypes.TEXT
        }
    }

    /**
     * 向前查看指定偏移位置的字符
     */
    private fun lookaheadChar(offset: Int): Char? {
        val idx = position + offset
        return if (idx < endOffset) buffer[idx] else null
    }

    /**
     * 查看指定位置之后的下一个非空白字符
     */
    private fun peekNextNonSpace(from: Int): Char? {
        var idx = from + 1
        while (idx < endOffset && buffer[idx].isWhitespace() && buffer[idx] != '\n' && buffer[idx] != '\r') {
            idx++
        }
        return if (idx < endOffset) buffer[idx] else null
    }

    /**
     * 判断当前位置是否可能是对话行（角色名后跟冒号）
     */
    private fun isLikelyDialogue(spacePos: Int): Boolean {
        var idx = spacePos + 1
        while (idx < endOffset && buffer[idx].isWhitespace() && buffer[idx] != '\n' && buffer[idx] != '\r') {
            idx++
        }
        if (idx < endOffset && (buffer[idx] == ':' || buffer[idx] == '\uFF1A')) {
            val beforeSpace = buffer.subSequence(tokenStart, spacePos)
            var hasCJK = false
            for (c in beforeSpace) {
                if (isCJKChar(c)) {
                    hasCJK = true
                    break
                }
            }
            return hasCJK || beforeSpace.all { it.isLetterOrDigit() || it == '_' }
        }
        return false
    }

    /**
     * 判断字符是否可以作为标识符的起始字符
     */
    private fun isIdentifierStart(ch: Char): Boolean {
        return ch.isLetter() || ch == '_' || isCJKChar(ch)
    }

    /**
     * 判断字符是否可以作为标识符的组成部分
     */
    private fun isIdentifierPart(ch: Char): Boolean {
        return ch.isLetterOrDigit() || ch == '_' || isCJKChar(ch)
    }

    /**
     * 判断字符是否为中文字符
     */
    private fun isCJKChar(ch: Char): Boolean {
        return ch in '\u4e00'..'\u9fff' || ch in '\u3400'..'\u4dbf' || ch in '\uf900'..'\ufaff'
    }

    /**
     * 判断字符是否为中文标点符号
     *
     * 包含常用的中文标点：，。！？、；""''
     */
    private fun isChinesePunctuation(ch: Char): Boolean {
        return when (ch) {
            '\uFF0C' -> true // ， 全角逗号
            '\u3002' -> true // 。 句号
            '\uFF01' -> true // ！ 全角感叹号
            '\uFF1F' -> true // ？ 全角问号
            '\u3001' -> true // 、 顿号
            '\uFF1B' -> true // ； 全角分号
            '\u201C' -> true // " 左双引号
            '\u201D' -> true // " 右双引号
            '\u2018' -> true // ' 左单引号
            '\u2019' -> true // ' 右单引号
            else -> false
        }
    }

    override fun getCurrentPosition(): LexerPosition {
        return LexerPositionImpl(position, getState())
    }

    override fun restore(position: LexerPosition) {
        this.buffer = buffer
        this.position = position.offset
        this.atLineStart = position.state == STATE_LINE_START
        this.tokenStart = this.position
        this.tokenEnd = this.position
        this.currentTokenType = null
        advance()
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset

    companion object {
        /**
         * 行首状态
         */
        const val STATE_LINE_START = 0

        /**
         * 行内状态
         */
        const val STATE_INLINE = 1
    }
}
