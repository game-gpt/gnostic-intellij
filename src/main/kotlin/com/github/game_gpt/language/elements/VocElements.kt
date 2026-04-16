package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.VocTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * VOC 模板区块 PSI 元素
 * 表示 `<template>...</template>` 区块
 */
class VocTemplateSectionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取模板中的所有组件元素
     */
    fun getWidgets(): List<VocWidgetElement> {
        return children.filterIsInstance<VocWidgetElement>()
    }

    /**
     * 获取模板中的所有文本内容
     */
    fun getTextContents(): List<PsiElement> {
        return children.filter { it.node?.elementType == VocTypes.TEXT_CONTENT }
    }

    override fun toString(): String = "Template"
}

/**
 * VOC 脚本区块 PSI 元素
 * 表示 `<script>...</script>` 区块
 */
class VocScriptSectionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取脚本中的所有 using 声明
     */
    fun getUsingDeclarations(): List<VocUsingElement> {
        return children.filterIsInstance<VocUsingElement>()
    }

    /**
     * 获取脚本中的所有变量声明
     */
    fun getVariables(): List<VocVariableElement> {
        return children.filterIsInstance<VocVariableElement>()
    }

    /**
     * 获取脚本中的所有函数声明
     */
    fun getFunctions(): List<VocFunctionElement> {
        return children.filterIsInstance<VocFunctionElement>()
    }

    override fun toString(): String = "Script"
}

/**
 * VOC 样式区块 PSI 元素
 * 表示 `<style>...</style>` 区块
 */
class VocStyleSectionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取样式中的所有规则
     */
    fun getRules(): List<VocStyleRuleElement> {
        return children.filterIsInstance<VocStyleRuleElement>()
    }

    override fun toString(): String = "Style"
}

/**
 * VOC 组件元素 PSI 元素
 * 表示 `<标签名 属性="值">内容</标签名>` 形式的组件
 */
class VocWidgetElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取标签名
     */
    fun getTagName(): String? {
        return node.findChildByType(VocTypes.WIDGET_TAG_NAME)?.text
    }

    /**
     * 获取标签名 PSI 元素
     */
    fun getTagNameElement(): PsiElement? {
        return node.findChildByType(VocTypes.WIDGET_TAG_NAME)?.psi
    }

    /**
     * 获取所有属性
     */
    fun getAttributes(): List<VocWidgetAttributeElement> {
        return children.filterIsInstance<VocWidgetAttributeElement>()
    }

    /**
     * 获取指定属性的值
     */
    fun getAttributeValue(name: String): String? {
        return getAttributes().find { it.getName() == name }?.getValue()
    }

    /**
     * 获取子组件
     */
    fun getWidgetChildren(): List<VocWidgetElement> {
        return children.filterIsInstance<VocWidgetElement>()
    }

    /**
     * 判断是否为自闭合标签
     */
    fun isSelfClosing(): Boolean {
        return node.findChildByType(VocTypes.SELF_CLOSE) != null
    }

    override fun toString(): String = "Widget<${getTagName()}>"
}

/**
 * VOC 组件属性 PSI 元素
 * 表示 `属性名="属性值"` 形式的属性
 */
class VocWidgetAttributeElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取属性名
     */
    override fun getName(): String? {
        return node.findChildByType(VocTypes.WIDGET_ATTRIBUTE_NAME)?.text
    }

    /**
     * 获取属性名标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(VocTypes.WIDGET_ATTRIBUTE_NAME)?.psi
    }

    /**
     * 重命名属性
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 获取属性值
     */
    fun getValue(): String? {
        return node.findChildByType(VocTypes.WIDGET_ATTRIBUTE_VALUE)?.text?.trim('"', '\'')
    }

    /**
     * 获取属性值 PSI 元素
     */
    fun getValueElement(): PsiElement? {
        return node.findChildByType(VocTypes.WIDGET_ATTRIBUTE_VALUE)?.psi
    }

    /**
     * 判断属性值是否为绑定表达式（使用花括号）
     */
    fun isBinding(): Boolean {
        val value = getValue() ?: return false
        return value.startsWith("{") && value.endsWith("}")
    }

    override fun toString(): String = "${getName()}=\"${getValue()}\""
}

/**
 * VOC 花括号表达式 PSI 元素
 * 表示 `{表达式}` 形式的绑定表达式
 */
class VocBraceExpressionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取表达式文本
     */
    fun getExpressionText(): String {
        return node.text.trim('{', '}')
    }

    override fun toString(): String = "{${getExpressionText()}}"
}

/**
 * VOC Using 声明 PSI 元素
 * 表示 `using 模块名;` 形式的导入声明
 */
class VocUsingElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取导入路径
     */
    fun getImportPath(): String? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Using[${getImportPath()}]"
}

/**
 * VOC 变量声明 PSI 元素
 * 表示 `let/const 变量名 = 值;` 形式的变量声明
 */
class VocVariableElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取变量名
     */
    override fun getName(): String? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.text
    }

    /**
     * 获取变量名标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.psi
    }

    /**
     * 重命名变量
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 判断是否为常量
     */
    fun isConst(): Boolean {
        return node.findChildByType(VocTypes.KEYWORD_CONST) != null
    }

    /**
     * 获取初始值表达式
     */
    fun getInitializer(): PsiElement? {
        return node.findChildByType(VocTypes.EXPRESSION)?.psi
    }

    override fun toString(): String = "${if (isConst()) "const" else "let"}[${getName()}]"
}

/**
 * VOC 函数声明 PSI 元素
 * 表示 `micro/mezzo/macro/fn 函数名(参数) { ... }` 形式的函数声明
 */
class VocFunctionElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取函数名
     */
    override fun getName(): String? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.text
    }

    /**
     * 获取函数名标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.psi
    }

    /**
     * 重命名函数
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 获取函数类型（micro/mezzo/macro/fn）
     */
    fun getFunctionType(): String? {
        return when {
            node.findChildByType(VocTypes.KEYWORD_MICRO) != null -> "micro"
            node.findChildByType(VocTypes.KEYWORD_MEZZO) != null -> "mezzo"
            node.findChildByType(VocTypes.KEYWORD_MACRO) != null -> "macro"
            node.findChildByType(VocTypes.KEYWORD_FN) != null -> "fn"
            else -> null
        }
    }

    /**
     * 获取参数列表
     */
    fun getParameters(): PsiElement? {
        return node.findChildByType(VocTypes.PARAMETER_LIST)?.psi
    }

    /**
     * 获取函数体
     */
    fun getBody(): PsiElement? {
        return node.findChildByType(VocTypes.BLOCK)?.psi
    }

    override fun toString(): String = "${getFunctionType()}[${getName()}]"
}

/**
 * VOC 样式规则 PSI 元素
 * 表示 `选择器 { 属性: 值; ... }` 形式的样式规则
 */
class VocStyleRuleElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取选择器
     */
    fun getSelector(): String? {
        return node.findChildByType(VocTypes.STYLE_SELECTOR)?.text
    }

    /**
     * 获取选择器 PSI 元素
     */
    fun getSelectorElement(): PsiElement? {
        return node.findChildByType(VocTypes.STYLE_SELECTOR)?.psi
    }

    /**
     * 获取所有样式属性
     */
    fun getProperties(): List<VocStylePropertyElement> {
        return children.filterIsInstance<VocStylePropertyElement>()
    }

    /**
     * 获取指定属性的值
     */
    fun getPropertyValue(name: String): String? {
        return getProperties().find { it.getName() == name }?.getValue()
    }

    override fun toString(): String = "StyleRule[${getSelector()}]"
}

/**
 * VOC 样式属性 PSI 元素
 * 表示 `属性名: 属性值;` 形式的样式属性
 */
class VocStylePropertyElement(node: ASTNode) : GnosticElement(node), PsiNameIdentifierOwner {

    /**
     * 获取属性名
     */
    override fun getName(): String? {
        return node.findChildByType(VocTypes.STYLE_PROPERTY_NAME)?.text
    }

    /**
     * 获取属性名标识符
     */
    override fun getNameIdentifier(): PsiElement? {
        return node.findChildByType(VocTypes.STYLE_PROPERTY_NAME)?.psi
    }

    /**
     * 重命名属性
     */
    override fun setName(name: String): PsiElement = this

    /**
     * 获取属性值
     */
    fun getValue(): String? {
        return node.findChildByType(VocTypes.STYLE_PROPERTY_VALUE)?.text?.trim()
    }

    /**
     * 获取属性值 PSI 元素
     */
    fun getValueElement(): PsiElement? {
        return node.findChildByType(VocTypes.STYLE_PROPERTY_VALUE)?.psi
    }

    override fun toString(): String = "${getName()}: ${getValue()}"
}

/**
 * VOC 标识符引用 PSI 元素
 * 表示对标识符的引用
 */
class VocIdentifierRefElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取引用的标识符名称
     */
    fun getIdentifierName(): String? {
        return node.findChildByType(VocTypes.IDENTIFIER)?.text
    }

    override fun toString(): String = "Ref[${getIdentifierName()}]"
}

/**
 * VOC 表达式 PSI 元素
 * 表示通用表达式
 */
class VocExpressionElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取表达式文本
     */
    fun getExpressionText(): String = node.text

    override fun toString(): String = "Expression"
}

/**
 * VOC 块 PSI 元素
 * 表示 `{ ... }` 形式的代码块
 */
class VocBlockElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取块中的所有语句
     */
    fun getStatements(): List<PsiElement> {
        return children.toList()
    }

    override fun toString(): String = "Block"
}

/**
 * VOC 参数列表 PSI 元素
 * 表示 `(参数1, 参数2, ...)` 形式的参数列表
 */
class VocParameterListElement(node: ASTNode) : GnosticElement(node) {

    /**
     * 获取所有参数
     */
    fun getParameters(): List<PsiElement> {
        return children.toList()
    }

    override fun toString(): String = "ParameterList"
}
