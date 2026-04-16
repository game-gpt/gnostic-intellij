package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.NoteTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Notedown 故事文件 PSI 元素
 * 表示整个 Notedown 文件的根节点
 */
class NoteStoryElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取文件中的所有场景
     */
    fun getScenes(): List<NoteSceneElement> {
        return children.filterIsInstance<NoteSceneElement>()
    }

    /**
     * 获取文件中的所有变量定义
     */
    fun getVariables(): List<NoteVariableElement> {
        return children.filterIsInstance<NoteVariableElement>()
    }

    /**
     * 获取文件中的所有 include 语句
     */
    fun getIncludes(): List<NoteIncludeElement> {
        return children.filterIsInstance<NoteIncludeElement>()
    }

    override fun toString(): String = "NoteStory"
}

/**
 * Notedown 场景 PSI 元素
 * 表示 `## 场景名` 形式的场景定义
 */
class NoteSceneElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取场景名称
     */
    override fun getName(): String? {
        return node.findChildByType(NoteTypes.SCENE_HEADER)?.text?.trimStart('#', ' ')
    }

    /**
     * 获取场景名称标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(NoteTypes.SCENE_HEADER)?.psi
    }

    /**
     * 重命名场景
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 获取场景体内容
     */
    fun getBody(): NoteSceneBodyElement? {
        return children.filterIsInstance<NoteSceneBodyElement>().firstOrNull()
    }

    /**
     * 获取场景中的所有对话
     */
    fun getDialogues(): List<NoteDialogueElement> {
        return children.filterIsInstance<NoteDialogueElement>()
    }

    /**
     * 获取场景中的所有选择块
     */
    fun getChoices(): List<NoteChoiceBlockElement> {
        return children.filterIsInstance<NoteChoiceBlockElement>()
    }

    override fun toString(): String = "Scene[${getName()}]"
}

/**
 * Notedown 场景体 PSI 元素
 * 表示场景的内容部分
 */
class NoteSceneBodyElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取场景体中的所有文本行
     */
    fun getTextLines(): List<NoteTextLineElement> {
        return children.filterIsInstance<NoteTextLineElement>()
    }

    /**
     * 获取场景体中的所有对话
     */
    fun getDialogues(): List<NoteDialogueElement> {
        return children.filterIsInstance<NoteDialogueElement>()
    }

    override fun toString(): String = "SceneBody"
}

/**
 * Notedown 文本行 PSI 元素
 * 表示场景中的普通文本行
 */
class NoteTextLineElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取文本内容
     */
    override fun getText(): String {
        return node.text
    }

    override fun toString(): String = "TextLine"
}

/**
 * Notedown 对话 PSI 元素
 * 表示 `角色名: 对话内容` 形式的对话行
 */
class NoteDialogueElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取说话者名称
     */
    fun getSpeaker(): String? {
        return node.findChildByType(NoteTypes.SPEAKER_NAME)?.text
    }

    /**
     * 获取对话文本
     */
    fun getDialogueText(): String? {
        return node.findChildByType(NoteTypes.DIALOGUE_TEXT)?.text
    }

    /**
     * 获取说话者 PSI 元素
     */
    fun getSpeakerElement(): PsiElement? {
        return node.findChildByType(NoteTypes.SPEAKER_NAME)?.psi
    }

    override fun toString(): String = "Dialogue[${getSpeaker()}]"
}

/**
 * Notedown 选择块 PSI 元素
 * 表示 `- [选项文本] -> 目标` 形式的选择块
 */
class NoteChoiceBlockElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取所有选项
     */
    fun getChoices(): List<NoteChoiceItemElement> {
        return children.filterIsInstance<NoteChoiceItemElement>()
    }

    override fun toString(): String = "ChoiceBlock[${getChoices().size} choices]"
}

/**
 * Notedown 选项 PSI 元素
 * 表示单个选项
 */
class NoteChoiceItemElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取选项文本
     */
    fun getChoiceText(): String? {
        return node.findChildByType(NoteTypes.CHOICE_TEXT)?.text
    }

    /**
     * 获取跳转目标
     */
    fun getJumpTarget(): String? {
        return node.findChildByType(NoteTypes.JUMP_TARGET)?.text
    }

    /**
     * 获取选项体（条件或命令）
     */
    fun getBody(): NoteChoiceBodyElement? {
        return children.filterIsInstance<NoteChoiceBodyElement>().firstOrNull()
    }

    override fun toString(): String = "Choice[${getChoiceText()}]"
}

/**
 * Notedown 选项体 PSI 元素
 * 表示选项后的条件或命令块
 */
class NoteChoiceBodyElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取所有命令调用
     */
    fun getCommands(): List<NoteCommandElement> {
        return children.filterIsInstance<NoteCommandElement>()
    }

    /**
     * 获取所有条件块
     */
    fun getConditionals(): List<NoteConditionalElement> {
        return children.filterIsInstance<NoteConditionalElement>()
    }

    override fun toString(): String = "ChoiceBody"
}

/**
 * Notedown 变量定义 PSI 元素
 * 表示 `let 变量名 = 值` 形式的变量定义
 */
class NoteVariableElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取变量名
     */
    override fun getName(): String? {
        return node.findChildByType(NoteTypes.IDENTIFIER)?.text
    }

    /**
     * 获取变量名标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(NoteTypes.IDENTIFIER)?.psi
    }

    /**
     * 重命名变量
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 获取变量值表达式
     */
    fun getValue(): PsiElement? {
        return node.findChildByType(NoteTypes.EXPRESSION)?.psi
    }

    override fun toString(): String = "Variable[${getName()}]"
}

/**
 * Notedown Include 语句 PSI 元素
 * 表示 `include "模块名"` 形式的包含语句
 */
class NoteIncludeElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取包含的模块名
     */
    fun getModuleName(): String? {
        return node.findChildByType(NoteTypes.MODULE_NAME)?.text
    }

    override fun toString(): String = "Include[${getModuleName()}]"
}

/**
 * Notedown 命令调用 PSI 元素
 * 表示 `%命令名(参数)` 形式的命令调用
 */
class NoteCommandElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取命令名
     */
    fun getCommandName(): String? {
        return node.findChildByType(NoteTypes.FUNCTION_NAME)?.text
    }

    /**
     * 获取参数列表
     */
    fun getArguments(): PsiElement? {
        return node.findChildByType(NoteTypes.ARGUMENT_LIST)?.psi
    }

    override fun toString(): String = "Command[${getCommandName()}]"
}

/**
 * Notedown 条件块 PSI 元素
 * 表示条件分支结构
 */
class NoteConditionalElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取条件表达式
     */
    fun getCondition(): PsiElement? {
        return node.findChildByType(NoteTypes.CONDITION_EXPRESSION)?.psi
    }

    /**
     * 获取条件分支
     */
    fun getBranches(): List<NoteConditionBranchElement> {
        return children.filterIsInstance<NoteConditionBranchElement>()
    }

    override fun toString(): String = "Conditional"
}

/**
 * Notedown 条件分支 PSI 元素
 * 表示单个条件分支
 */
class NoteConditionBranchElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 判断是否为 else 分支
     */
    fun isElseBranch(): Boolean {
        return node.findChildByType(NoteTypes.KEYWORD_ELSE) != null
    }

    /**
     * 获取分支内容
     */
    fun getContent(): List<PsiElement> {
        return children.toList()
    }

    override fun toString(): String = if (isElseBranch()) "ElseBranch" else "ConditionBranch"
}

/**
 * Notedown 跳转语句 PSI 元素
 * 表示 `-> 目标场景` 形式的跳转语句
 */
class NoteJumpElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取跳转目标
     */
    fun getTarget(): String? {
        return node.findChildByType(NoteTypes.JUMP_TARGET)?.text
    }

    override fun toString(): String = "Jump[${getTarget()}]"
}

/**
 * Notedown 变量操作 PSI 元素
 * 表示变量赋值或修改操作
 */
class NoteVariableOpElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取变量名
     */
    fun getVariableName(): String? {
        return node.findChildByType(NoteTypes.IDENTIFIER)?.text
    }

    /**
     * 获取操作符
     */
    fun getOperator(): String? {
        val text = node.text
        return when {
            text.contains("+=") -> "+="
            text.contains("-=") -> "-="
            text.contains("=") -> "="
            else -> null
        }
    }

    override fun toString(): String = "VarOp[${getVariableName()}]"
}

/**
 * Notedown 表达式 PSI 元素
 * 表示通用表达式
 */
class NoteExpressionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取表达式文本
     */
    fun getExpressionText(): String = node.text

    override fun toString(): String = "Expression"
}
