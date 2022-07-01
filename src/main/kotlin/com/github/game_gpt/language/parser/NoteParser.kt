package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.config.NotedownLanguageConfig
import com.github.game_gpt.language.types.NoteTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

/**
 * Notedown 语言的语法分析器
 *
 * 实现递归下降解析，构建正确的树状 AST 结构。
 * 解析器识别 Notedown 格式的所有语法结构，包括场景定义、选项分支、条件判断、
 * 命令调用、变量操作等，并将它们组织为嵌套的树状结构而非平铺的列表。
 */
class NoteParser(val config: NotedownLanguageConfig) : PsiParser, LightPsiParser {

    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val marker = builder.mark()
        parseFile(builder)
        marker.done(root)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType?, builder: PsiBuilder?) {
        if (builder != null && root != null) {
            val marker = builder.mark()
            parseFile(builder)
            marker.done(root)
        }
    }

    /**
     * 解析文件内容
     *
     * 文件由顶层声明组成：注释、变量定义、包含语句和场景定义。
     */
    private fun parseFile(builder: PsiBuilder) {
        while (builder.tokenType != null) {
            parseTopLevelDeclaration(builder)
        }
    }

    /**
     * 解析顶层声明
     *
     * 识别文件顶部的各种声明语句。
     */
    private fun parseTopLevelDeclaration(builder: PsiBuilder) {
        when (builder.tokenType) {
            NoteTypes.COMMENT -> parseComment(builder)
            NoteTypes.PERCENT -> parsePercentDirective(builder)
            NoteTypes.SCENE_MARKER, NoteTypes.DOUBLE_EQUALS -> parseSceneDefinition(builder)
            NoteTypes.HORIZONTAL_RULE -> parseSeparator(builder)
            NoteTypes.INDENT -> skipIndent(builder)
            NoteTypes.NEWLINE -> skipNewline(builder)
            else -> builder.advanceLexer()
        }
    }

    /**
     * 解析百分号指令
     *
     * 识别 % let（变量定义）和 % include（文件包含）以及 % module::function()（命令调用）。
     */
    private fun parsePercentDirective(builder: PsiBuilder) {
        if (builder.tokenType != NoteTypes.PERCENT) {
            builder.advanceLexer()
            return
        }

        val nextType = lookahead(builder)
        when (nextType) {
            NoteTypes.KEYWORD_LET -> parseVariableDefinition(builder)
            NoteTypes.KEYWORD_INCLUDE -> parseIncludeStatement(builder)
            NoteTypes.IDENTIFIER -> parseCommandCall(builder)
            else -> {
                builder.advanceLexer()
            }
        }
    }

    /**
     * 解析注释
     */
    private fun parseComment(builder: PsiBuilder) {
        builder.advanceLexer()
        consumeNewlines(builder)
    }

    /**
     * 解析变量定义
     *
     * 格式：% let 变量名 = 初始值
     */
    private fun parseVariableDefinition(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.PERCENT)
        expectToken(builder, NoteTypes.KEYWORD_LET)
        expectToken(builder, NoteTypes.IDENTIFIER)
        if (builder.tokenType == NoteTypes.EQUALS) {
            builder.advanceLexer()
            parseExpressionUntilNewline(builder)
        }
        consumeNewlines(builder)
        marker.done(NoteTypes.VARIABLE_DEFINITION)
    }

    /**
     * 解析包含语句
     *
     * 格式：% include 文件路径
     */
    private fun parseIncludeStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.PERCENT)
        expectToken(builder, NoteTypes.KEYWORD_INCLUDE)
        parseExpressionUntilNewline(builder)
        consumeNewlines(builder)
        marker.done(NoteTypes.INCLUDE_STATEMENT)
    }

    /**
     * 解析场景定义
     *
     * 格式：== 场景名 == 后跟场景体内容
     */
    private fun parseSceneDefinition(builder: PsiBuilder) {
        val marker = builder.mark()
        parseSceneHeader(builder)
        parseSceneBody(builder)
        marker.done(NoteTypes.SCENE_DEFINITION)
    }

    /**
     * 解析场景头部
     *
     * 格式：== 场景名 ==
     */
    private fun parseSceneHeader(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.SCENE_MARKER)
        if (builder.tokenType == NoteTypes.IDENTIFIER) {
            builder.advanceLexer()
        } else if (builder.tokenType == NoteTypes.TEXT) {
            builder.advanceLexer()
        }
        if (builder.tokenType == NoteTypes.SCENE_MARKER || builder.tokenType == NoteTypes.DOUBLE_EQUALS) {
            builder.advanceLexer()
        }
        consumeNewlines(builder)
        marker.done(NoteTypes.SCENE_HEADER)
    }

    /**
     * 解析场景体
     *
     * 场景体包含直到下一个场景定义或文件结束的所有语句。
     */
    private fun parseSceneBody(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != NoteTypes.SCENE_MARKER &&
            builder.tokenType != NoteTypes.DOUBLE_EQUALS
        ) {
            parseStatement(builder)
        }
        marker.done(NoteTypes.SCENE_BODY)
    }

    /**
     * 解析语句
     *
     * 识别场景体内的各种语句类型。
     */
    private fun parseStatement(builder: PsiBuilder) {
        when (builder.tokenType) {
            NoteTypes.COMMENT -> parseComment(builder)
            NoteTypes.PERCENT -> parsePercentDirective(builder)
            NoteTypes.CHOICE_MARKER -> parseChoiceBlock(builder)
            NoteTypes.BRACE_L -> parseConditionalBlock(builder)
            NoteTypes.TILDE -> parseVariableOperation(builder)
            NoteTypes.ARROW -> parseJumpStatement(builder)
            NoteTypes.HORIZONTAL_RULE -> parseSeparator(builder)
            NoteTypes.SCENE_MARKER, NoteTypes.DOUBLE_EQUALS -> return
            NoteTypes.INDENT -> skipIndent(builder)
            NoteTypes.NEWLINE -> skipNewline(builder)
            NoteTypes.IDENTIFIER -> parseDialogueOrText(builder)
            NoteTypes.TEXT -> parseTextLine(builder)
            else -> builder.advanceLexer()
        }
    }

    /**
     * 解析对话行或纯文本行
     *
     * 判断标识符后是否跟冒号来区分对话行和纯文本行。
     */
    private fun parseDialogueOrText(builder: PsiBuilder) {
        val nextType = lookahead(builder)
        if (nextType == NoteTypes.COLON) {
            parseDialogueLine(builder)
        } else {
            parseTextLine(builder)
        }
    }

    /**
     * 解析对话行
     *
     * 格式：角色名：对话文本
     */
    private fun parseDialogueLine(builder: PsiBuilder) {
        val marker = builder.mark()
        parseSpeakerName(builder)
        expectToken(builder, NoteTypes.COLON)
        parseDialogueText(builder)
        consumeNewlines(builder)
        marker.done(NoteTypes.DIALOGUE_LINE)
    }

    /**
     * 解析说话者名称
     */
    private fun parseSpeakerName(builder: PsiBuilder) {
        val marker = builder.mark()
        if (builder.tokenType == NoteTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        marker.done(NoteTypes.SPEAKER_NAME)
    }

    /**
     * 解析对话文本
     *
     * 对话文本包含冒号后到行尾的所有内容。
     */
    private fun parseDialogueText(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != NoteTypes.NEWLINE &&
            builder.tokenType != NoteTypes.INDENT
        ) {
            if (builder.tokenType == NoteTypes.TEXT ||
                builder.tokenType == NoteTypes.IDENTIFIER ||
                builder.tokenType == NoteTypes.STRING ||
                builder.tokenType == NoteTypes.NUMBER
            ) {
                builder.advanceLexer()
            } else {
                break
            }
        }
        marker.done(NoteTypes.DIALOGUE_TEXT)
    }

    /**
     * 解析纯文本行
     */
    private fun parseTextLine(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != NoteTypes.NEWLINE &&
            builder.tokenType != NoteTypes.INDENT
        ) {
            if (builder.tokenType == NoteTypes.TEXT ||
                builder.tokenType == NoteTypes.IDENTIFIER ||
                builder.tokenType == NoteTypes.STRING ||
                builder.tokenType == NoteTypes.NUMBER
            ) {
                builder.advanceLexer()
            } else {
                break
            }
        }
        consumeNewlines(builder)
        marker.done(NoteTypes.TEXT_LINE)
    }

    /**
     * 解析选项块
     *
     * 连续的选项项组成一个选项块。
     */
    private fun parseChoiceBlock(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType == NoteTypes.CHOICE_MARKER ||
            builder.tokenType == NoteTypes.INDENT
        ) {
            if (builder.tokenType == NoteTypes.INDENT) {
                val nextType = lookahead(builder)
                if (nextType == NoteTypes.CHOICE_MARKER) {
                    skipIndent(builder)
                } else {
                    break
                }
            }
            if (builder.tokenType == NoteTypes.CHOICE_MARKER) {
                parseChoiceItem(builder)
            }
        }
        marker.done(NoteTypes.CHOICE_BLOCK)
    }

    /**
     * 解析选项项
     *
     * 格式：* [选项文本] 或 * {条件} [选项文本]，后跟缩进的选项体。
     */
    private fun parseChoiceItem(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.CHOICE_MARKER)

        if (builder.tokenType == NoteTypes.BRACE_L) {
            parseConditionExpression(builder)
        }

        parseChoiceText(builder)
        consumeNewlines(builder)
        parseChoiceBody(builder)
        marker.done(NoteTypes.CHOICE_ITEM)
    }

    /**
     * 解析条件表达式
     *
     * 格式：{条件表达式}
     */
    private fun parseConditionExpression(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.BRACE_L)
        while (builder.tokenType != null && builder.tokenType != NoteTypes.BRACE_R) {
            if (builder.tokenType == NoteTypes.NEWLINE || builder.tokenType == NoteTypes.INDENT) {
                break
            }
            builder.advanceLexer()
        }
        expectToken(builder, NoteTypes.BRACE_R)
        marker.done(NoteTypes.CONDITION_EXPRESSION)
    }

    /**
     * 解析选项文本
     *
     * 格式：[选项文本]
     */
    private fun parseChoiceText(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.BRACKET_L)
        while (builder.tokenType != null && builder.tokenType != NoteTypes.BRACKET_R) {
            if (builder.tokenType == NoteTypes.TEXT ||
                builder.tokenType == NoteTypes.IDENTIFIER ||
                builder.tokenType == NoteTypes.STRING ||
                builder.tokenType == NoteTypes.NUMBER
            ) {
                builder.advanceLexer()
            } else {
                break
            }
        }
        expectToken(builder, NoteTypes.BRACKET_R)
        marker.done(NoteTypes.CHOICE_TEXT)
    }

    /**
     * 解析选项体
     *
     * 选项体包含缩进的语句内容。
     */
    private fun parseChoiceBody(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType == NoteTypes.INDENT) {
            skipIndent(builder)
            while (builder.tokenType != null &&
                builder.tokenType != NoteTypes.NEWLINE
            ) {
                if (builder.tokenType == NoteTypes.INDENT) break
                parseStatement(builder)
            }
            if (builder.tokenType == NoteTypes.NEWLINE) {
                skipNewline(builder)
            }
        }
        marker.done(NoteTypes.CHOICE_BODY)
    }

    /**
     * 解析条件块
     *
     * 格式：{ - 条件: 内容 - else: 内容 }
     */
    private fun parseConditionalBlock(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.BRACE_L)
        consumeNewlines(builder)

        while (builder.tokenType != null && builder.tokenType != NoteTypes.BRACE_R) {
            if (builder.tokenType == NoteTypes.INDENT) {
                skipIndent(builder)
                continue
            }
            if (builder.tokenType == NoteTypes.NEWLINE) {
                skipNewline(builder)
                continue
            }
            if (builder.tokenType == NoteTypes.DASH) {
                parseConditionBranch(builder)
            } else if (builder.tokenType == NoteTypes.KEYWORD_ELSE) {
                parseElseBranch(builder)
            } else {
                builder.advanceLexer()
            }
        }

        expectToken(builder, NoteTypes.BRACE_R)
        consumeNewlines(builder)
        marker.done(NoteTypes.CONDITIONAL_BLOCK)
    }

    /**
     * 解析条件分支
     *
     * 格式：- 条件表达式: 分支内容
     */
    private fun parseConditionBranch(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.DASH)
        if (builder.tokenType == NoteTypes.KEYWORD_ELSE) {
            expectToken(builder, NoteTypes.KEYWORD_ELSE)
            expectToken(builder, NoteTypes.COLON)
            consumeNewlines(builder)
            parseBranchBody(builder)
        } else {
            parseConditionExpressionInBranch(builder)
            expectToken(builder, NoteTypes.COLON)
            consumeNewlines(builder)
            parseBranchBody(builder)
        }
        marker.done(NoteTypes.CONDITION_BRANCH)
    }

    /**
     * 解析 else 分支
     *
     * 格式：else: 分支内容
     */
    private fun parseElseBranch(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.KEYWORD_ELSE)
        expectToken(builder, NoteTypes.COLON)
        consumeNewlines(builder)
        parseBranchBody(builder)
        marker.done(NoteTypes.CONDITION_BRANCH)
    }

    /**
     * 解析条件分支中的条件表达式
     */
    private fun parseConditionExpressionInBranch(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null && builder.tokenType != NoteTypes.COLON) {
            if (builder.tokenType == NoteTypes.NEWLINE) break
            builder.advanceLexer()
        }
        marker.done(NoteTypes.CONDITION_EXPRESSION)
    }

    /**
     * 解析条件分支体
     *
     * 分支体包含缩进的语句内容。
     */
    private fun parseBranchBody(builder: PsiBuilder) {
        while (builder.tokenType == NoteTypes.INDENT) {
            skipIndent(builder)
            while (builder.tokenType != null &&
                builder.tokenType != NoteTypes.NEWLINE
            ) {
                if (builder.tokenType == NoteTypes.INDENT) break
                if (builder.tokenType == NoteTypes.BRACE_R) break
                if (builder.tokenType == NoteTypes.DASH) break
                parseStatement(builder)
            }
            if (builder.tokenType == NoteTypes.NEWLINE) {
                skipNewline(builder)
            }
        }
    }

    /**
     * 解析命令调用
     *
     * 格式：%module::function(参数列表)
     */
    private fun parseCommandCall(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.PERCENT)
        parseModuleName(builder)
        expectToken(builder, NoteTypes.DOUBLE_COLON)
        parseFunctionName(builder)
        if (builder.tokenType == NoteTypes.PAREN_L) {
            parseArgumentList(builder)
        }
        consumeNewlines(builder)
        marker.done(NoteTypes.COMMAND_CALL)
    }

    /**
     * 解析模块名
     */
    private fun parseModuleName(builder: PsiBuilder) {
        val marker = builder.mark()
        if (builder.tokenType == NoteTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        marker.done(NoteTypes.MODULE_NAME)
    }

    /**
     * 解析函数名
     */
    private fun parseFunctionName(builder: PsiBuilder) {
        val marker = builder.mark()
        if (builder.tokenType == NoteTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        marker.done(NoteTypes.FUNCTION_NAME)
    }

    /**
     * 解析参数列表
     *
     * 格式：(参数1, 参数2, ...)
     */
    private fun parseArgumentList(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.PAREN_L)
        while (builder.tokenType != null && builder.tokenType != NoteTypes.PAREN_R) {
            if (builder.tokenType == NoteTypes.COMMA) {
                builder.advanceLexer()
                continue
            }
            if (builder.tokenType == NoteTypes.STRING ||
                builder.tokenType == NoteTypes.NUMBER ||
                builder.tokenType == NoteTypes.IDENTIFIER ||
                builder.tokenType == NoteTypes.KEYWORD_TRUE ||
                builder.tokenType == NoteTypes.KEYWORD_FALSE ||
                builder.tokenType == NoteTypes.TEXT
            ) {
                builder.advanceLexer()
            } else {
                break
            }
        }
        expectToken(builder, NoteTypes.PAREN_R)
        marker.done(NoteTypes.ARGUMENT_LIST)
    }

    /**
     * 解析变量操作
     *
     * 格式：~ %{表达式}
     */
    private fun parseVariableOperation(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.TILDE)
        if (builder.tokenType == NoteTypes.PERCENT_CURLY) {
            builder.advanceLexer()
            parseExpressionUntilBraceR(builder)
            expectToken(builder, NoteTypes.BRACE_R)
        } else {
            parseExpressionUntilNewline(builder)
        }
        consumeNewlines(builder)
        marker.done(NoteTypes.VARIABLE_OPERATION)
    }

    /**
     * 解析跳转语句
     *
     * 格式：-> 目标
     */
    private fun parseJumpStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.ARROW)
        parseJumpTarget(builder)
        consumeNewlines(builder)
        marker.done(NoteTypes.JUMP_STATEMENT)
    }

    /**
     * 解析跳转目标
     */
    private fun parseJumpTarget(builder: PsiBuilder) {
        val marker = builder.mark()
        if (builder.tokenType == NoteTypes.IDENTIFIER) {
            builder.advanceLexer()
        } else if (builder.tokenType == NoteTypes.KEYWORD_DONE) {
            builder.advanceLexer()
        } else if (builder.tokenType == NoteTypes.TEXT) {
            builder.advanceLexer()
        }
        if (builder.tokenType == NoteTypes.DOT) {
            builder.advanceLexer()
            if (builder.tokenType == NoteTypes.IDENTIFIER) {
                builder.advanceLexer()
            }
        }
        marker.done(NoteTypes.JUMP_TARGET)
    }

    /**
     * 解析分隔线
     */
    private fun parseSeparator(builder: PsiBuilder) {
        val marker = builder.mark()
        expectToken(builder, NoteTypes.HORIZONTAL_RULE)
        consumeNewlines(builder)
        marker.done(NoteTypes.SEPARATOR)
    }

    /**
     * 解析表达式直到换行符
     */
    private fun parseExpressionUntilNewline(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != NoteTypes.NEWLINE &&
            builder.tokenType != NoteTypes.INDENT
        ) {
            builder.advanceLexer()
        }
        marker.done(NoteTypes.EXPRESSION)
    }

    /**
     * 解析表达式直到右大括号
     */
    private fun parseExpressionUntilBraceR(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null && builder.tokenType != NoteTypes.BRACE_R) {
            if (builder.tokenType == NoteTypes.NEWLINE) break
            builder.advanceLexer()
        }
        marker.done(NoteTypes.EXPRESSION)
    }

    /**
     * 向前查看下一个非空白、非缩进、非换行的 token 类型
     */
    private fun lookahead(builder: PsiBuilder): IElementType? {
        val marker = builder.mark()
        var type = builder.tokenType
        while (type == NoteTypes.INDENT || type == NoteTypes.NEWLINE) {
            builder.advanceLexer()
            type = builder.tokenType
        }
        marker.rollbackTo()
        return type
    }

    /**
     * 消费期望的 token，如果不匹配则跳过
     */
    private fun expectToken(builder: PsiBuilder, expected: IElementType) {
        if (builder.tokenType == expected) {
            builder.advanceLexer()
        }
    }

    /**
     * 跳过缩进 token
     */
    private fun skipIndent(builder: PsiBuilder) {
        if (builder.tokenType == NoteTypes.INDENT) {
            builder.advanceLexer()
        }
    }

    /**
     * 跳过换行 token
     */
    private fun skipNewline(builder: PsiBuilder) {
        if (builder.tokenType == NoteTypes.NEWLINE) {
            builder.advanceLexer()
        }
    }

    /**
     * 消费连续的换行和缩进 token
     */
    private fun consumeNewlines(builder: PsiBuilder) {
        while (builder.tokenType == NoteTypes.NEWLINE || builder.tokenType == NoteTypes.INDENT) {
            builder.advanceLexer()
        }
    }
}
