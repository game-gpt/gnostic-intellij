package com.github.game_gpt.language.parser

import com.github.game_gpt.language.types.VocTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

class VocParser : PsiParser, LightPsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val marker = builder.mark()
        while (builder.tokenType != null) {
            if (isSectionStart(builder)) {
                val tagName = peekTagName(builder)
                when (tagName) {
                    "template" -> parseTemplateSection(builder)
                    "script" -> parseScriptSection(builder)
                    "style" -> parseStyleSection(builder)
                    else -> builder.advanceLexer()
                }
            } else {
                builder.advanceLexer()
            }
        }
        marker.done(root)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType?, builder: PsiBuilder?) {
        if (builder != null && root != null) {
            val marker = builder.mark()
            while (builder.tokenType != null) {
                if (isSectionStart(builder)) {
                    val tagName = peekTagName(builder)
                    when (tagName) {
                        "template" -> parseTemplateSection(builder)
                        "script" -> parseScriptSection(builder)
                        "style" -> parseStyleSection(builder)
                        else -> builder.advanceLexer()
                    }
                } else {
                    builder.advanceLexer()
                }
            }
            marker.done(root)
        }
    }

    private fun isSectionStart(builder: PsiBuilder): Boolean {
        if (builder.tokenType != VocTypes.TAG_START) return false
        val tagName = peekTagName(builder)
        return tagName == "template" || tagName == "script" || tagName == "style"
    }

    private fun peekTagName(builder: PsiBuilder): String? {
        val marker = builder.mark()
        if (builder.tokenType == VocTypes.TAG_START) {
            builder.advanceLexer()
        }
        val tagName = if (builder.tokenType == VocTypes.TAG_NAME) {
            builder.tokenText?.toString()
        } else {
            null
        }
        marker.rollbackTo()
        return tagName
    }

    private fun peekEndTagName(builder: PsiBuilder): String? {
        val marker = builder.mark()
        if (builder.tokenType == VocTypes.XML_END_TAG_START) {
            builder.advanceLexer()
        }
        val tagName = if (builder.tokenType == VocTypes.TAG_NAME) {
            builder.tokenText?.toString()
        } else {
            null
        }
        marker.rollbackTo()
        return tagName
    }

    // ==================== Template Section ====================

    private fun parseTemplateSection(builder: PsiBuilder) {
        val sectionMarker = builder.mark()
        
        if (builder.tokenType == VocTypes.TAG_START) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.TAG_NAME) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.GT) {
            builder.advanceLexer()
        }

        while (builder.tokenType != null && !isTemplateEndTag(builder)) {
            when (builder.tokenType) {
                VocTypes.TAG_START -> parseXmlElement(builder)
                VocTypes.LBRACE -> parseBraceExpression(builder)
                VocTypes.IDENTIFIER -> parseTextContent(builder)
                else -> builder.advanceLexer()
            }
        }

        if (isTemplateEndTag(builder)) {
            if (builder.tokenType == VocTypes.XML_END_TAG_START) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.TAG_NAME) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.GT) {
                builder.advanceLexer()
            }
        }

        sectionMarker.done(VocTypes.TEMPLATE_SECTION)
    }

    private fun parseTextContent(builder: PsiBuilder) {
        val textMarker = builder.mark()
        builder.advanceLexer()
        textMarker.done(VocTypes.TEXT_CONTENT)
    }

    private fun isTemplateEndTag(builder: PsiBuilder): Boolean {
        if (builder.tokenType != VocTypes.XML_END_TAG_START) return false
        val tagName = peekEndTagName(builder)
        return tagName == "template"
    }

    private fun parseXmlElement(builder: PsiBuilder) {
        val elementMarker = builder.mark()
        
        if (builder.tokenType == VocTypes.TAG_START) {
            builder.advanceLexer()
        }

        if (builder.tokenType == VocTypes.TAG_NAME) {
            val nameMarker = builder.mark()
            builder.advanceLexer()
            nameMarker.done(VocTypes.WIDGET_TAG_NAME)
        }

        while (builder.tokenType != null &&
            builder.tokenType != VocTypes.GT &&
            builder.tokenType != VocTypes.SELF_CLOSE
        ) {
            if (builder.tokenType == VocTypes.IDENTIFIER || builder.tokenType == VocTypes.COLON) {
                parseXmlAttribute(builder)
            } else {
                builder.advanceLexer()
            }
        }

        val isSelfClosing = builder.tokenType == VocTypes.SELF_CLOSE
        if (builder.tokenType == VocTypes.GT || builder.tokenType == VocTypes.SELF_CLOSE) {
            builder.advanceLexer()
        }

        if (!isSelfClosing) {
            while (builder.tokenType != null && !isElementClosingTag(builder)) {
                when (builder.tokenType) {
                    VocTypes.TAG_START -> parseXmlElement(builder)
                    VocTypes.LBRACE -> parseBraceExpression(builder)
                    VocTypes.IDENTIFIER -> parseTextContent(builder)
                    else -> builder.advanceLexer()
                }
            }

            if (builder.tokenType == VocTypes.XML_END_TAG_START) {
                builder.advanceLexer()
                if (builder.tokenType == VocTypes.TAG_NAME) {
                    builder.advanceLexer()
                }
                if (builder.tokenType == VocTypes.GT) {
                    builder.advanceLexer()
                }
            }
        }

        elementMarker.done(VocTypes.WIDGET_ELEMENT)
    }

    private fun isElementClosingTag(builder: PsiBuilder): Boolean {
        return builder.tokenType == VocTypes.XML_END_TAG_START
    }

    private fun parseXmlAttribute(builder: PsiBuilder) {
        val attrMarker = builder.mark()

        val nameMarker = builder.mark()
        if (builder.tokenType == VocTypes.COLON) {
            builder.advanceLexer()
            if (builder.tokenType == VocTypes.IDENTIFIER) {
                builder.advanceLexer()
            }
        } else {
            builder.advanceLexer()
        }
        nameMarker.done(VocTypes.WIDGET_ATTRIBUTE_NAME)

        if (builder.tokenType == VocTypes.EQUAL) {
            builder.advanceLexer()
            if (builder.tokenType == VocTypes.LITERAL_STRING) {
                val valueMarker = builder.mark()
                builder.advanceLexer()
                valueMarker.done(VocTypes.WIDGET_ATTRIBUTE_VALUE)
            } else if (builder.tokenType == VocTypes.LBRACE) {
                parseBraceExpression(builder)
            }
        }

        attrMarker.done(VocTypes.WIDGET_ATTRIBUTE)
    }

    private fun parseBraceExpression(builder: PsiBuilder) {
        if (builder.tokenType != VocTypes.LBRACE) return

        val contentMarker = builder.mark()
        var depth = 0

        builder.advanceLexer()
        depth++

        while (builder.tokenType != null && depth > 0) {
            when (builder.tokenType) {
                VocTypes.LBRACE -> {
                    depth++
                    builder.advanceLexer()
                }
                VocTypes.RBRACE -> {
                    depth--
                    builder.advanceLexer()
                }
                else -> builder.advanceLexer()
            }
        }

        contentMarker.done(VocTypes.BRACE_EXPRESSION)
    }

    // ==================== Script Section ====================

    private fun parseScriptSection(builder: PsiBuilder) {
        val sectionMarker = builder.mark()
        
        if (builder.tokenType == VocTypes.TAG_START) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.TAG_NAME) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.GT) {
            builder.advanceLexer()
        }

        while (builder.tokenType != null && !isScriptEndTag(builder)) {
            parseScriptDeclaration(builder)
        }

        if (isScriptEndTag(builder)) {
            if (builder.tokenType == VocTypes.XML_END_TAG_START) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.TAG_NAME) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.GT) {
                builder.advanceLexer()
            }
        }

        sectionMarker.done(VocTypes.SCRIPT_SECTION)
    }

    private fun isScriptEndTag(builder: PsiBuilder): Boolean {
        if (builder.tokenType != VocTypes.XML_END_TAG_START) return false
        val tagName = peekEndTagName(builder)
        return tagName == "script"
    }

    private fun parseScriptDeclaration(builder: PsiBuilder) {
        when (builder.tokenType) {
            VocTypes.KEYWORD_USING -> parseScriptUsing(builder)
            VocTypes.KEYWORD_LET -> parseScriptLet(builder)
            VocTypes.KEYWORD_CONST -> parseScriptConst(builder)
            VocTypes.KEYWORD_MICRO -> parseScriptMicro(builder)
            VocTypes.KEYWORD_MEZZO -> parseScriptMezzo(builder)
            VocTypes.KEYWORD_MACRO -> parseScriptMacro(builder)
            VocTypes.KEYWORD_FN -> parseScriptFunction(builder)
            else -> parseScriptExpressionStatement(builder)
        }
    }

    private fun parseScriptUsing(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseScriptIdentifierPath(builder)
        if (builder.tokenType == VocTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.USING_DECLARATION)
    }

    private fun parseScriptLet(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.COLON) {
            builder.advanceLexer()
            parseScriptTypeReference(builder)
        }
        if (builder.tokenType == VocTypes.EQUALS) {
            builder.advanceLexer()
            parseScriptExpression(builder)
        }
        if (builder.tokenType == VocTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.LET_DECLARATION)
    }

    private fun parseScriptConst(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.COLON) {
            builder.advanceLexer()
            parseScriptTypeReference(builder)
        }
        if (builder.tokenType == VocTypes.EQUALS) {
            builder.advanceLexer()
            parseScriptExpression(builder)
        }
        if (builder.tokenType == VocTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.CONST_DECLARATION)
    }

    private fun parseScriptMicro(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        parseScriptParameterList(builder)
        parseScriptBlock(builder)
        marker.done(VocTypes.MICRO_DECLARATION)
    }

    private fun parseScriptMezzo(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        parseScriptParameterList(builder)
        parseScriptBlock(builder)
        marker.done(VocTypes.MEZZO_DECLARATION)
    }

    private fun parseScriptMacro(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        parseScriptParameterList(builder)
        parseScriptBlock(builder)
        marker.done(VocTypes.MACRO_DECLARATION)
    }

    private fun parseScriptFunction(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        parseScriptParameterList(builder)
        if (builder.tokenType == VocTypes.ARROW) {
            builder.advanceLexer()
            parseScriptTypeReference(builder)
        }
        parseScriptBlock(builder)
        marker.done(VocTypes.FUNCTION_DECLARATION)
    }

    private fun parseScriptBlock(builder: PsiBuilder) {
        if (builder.tokenType != VocTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != VocTypes.RBRACE) {
            parseScriptDeclaration(builder)
        }
        if (builder.tokenType == VocTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.BLOCK)
    }

    private fun parseScriptParameterList(builder: PsiBuilder) {
        if (builder.tokenType != VocTypes.LPAREN) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != VocTypes.RPAREN) {
            if (builder.tokenType == VocTypes.COMMA) {
                builder.advanceLexer()
                continue
            }
            if (builder.tokenType == VocTypes.IDENTIFIER) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.COLON) {
                builder.advanceLexer()
                parseScriptTypeReference(builder)
            }
        }
        if (builder.tokenType == VocTypes.RPAREN) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.PARAMETER_LIST)
    }

    private fun parseScriptTypeReference(builder: PsiBuilder) {
        parseScriptIdentifierPath(builder)
    }

    private fun parseScriptIdentifierPath(builder: PsiBuilder) {
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        while (builder.tokenType == VocTypes.DOUBLE_COLON || builder.tokenType == VocTypes.DOT) {
            builder.advanceLexer()
            if (builder.tokenType == VocTypes.IDENTIFIER) {
                builder.advanceLexer()
            }
        }
    }

    private fun parseScriptExpression(builder: PsiBuilder) {
        val marker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != VocTypes.SEMICOLON &&
            builder.tokenType != VocTypes.RBRACE
        ) {
            if (builder.tokenType == VocTypes.LBRACE) {
                parseScriptBlock(builder)
            } else if (builder.tokenType == VocTypes.LPAREN) {
                parseScriptArgumentList(builder)
            } else {
                builder.advanceLexer()
            }
        }
        marker.done(VocTypes.EXPRESSION)
    }

    private fun parseScriptArgumentList(builder: PsiBuilder) {
        if (builder.tokenType != VocTypes.LPAREN) return
        val marker = builder.mark()
        builder.advanceLexer()
        var depth = 0
        while (builder.tokenType != null && (depth > 0 || builder.tokenType != VocTypes.RPAREN)) {
            when (builder.tokenType) {
                VocTypes.LPAREN -> { depth++; builder.advanceLexer() }
                VocTypes.RPAREN -> { depth--; builder.advanceLexer() }
                VocTypes.LBRACE -> parseScriptBlock(builder)
                else -> builder.advanceLexer()
            }
        }
        marker.done(VocTypes.PARAMETER_LIST)
    }

    private fun parseScriptExpressionStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        parseScriptExpression(builder)
        if (builder.tokenType == VocTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(VocTypes.EXPRESSION_STATEMENT)
    }

    // ==================== Style Section ====================

    private fun parseStyleSection(builder: PsiBuilder) {
        val sectionMarker = builder.mark()
        
        if (builder.tokenType == VocTypes.TAG_START) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.TAG_NAME) {
            builder.advanceLexer()
        }
        if (builder.tokenType == VocTypes.GT) {
            builder.advanceLexer()
        }

        while (builder.tokenType != null && !isStyleEndTag(builder)) {
            parseStyleRule(builder)
        }

        if (isStyleEndTag(builder)) {
            if (builder.tokenType == VocTypes.XML_END_TAG_START) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.TAG_NAME) {
                builder.advanceLexer()
            }
            if (builder.tokenType == VocTypes.GT) {
                builder.advanceLexer()
            }
        }

        sectionMarker.done(VocTypes.STYLE_SECTION)
    }

    private fun isStyleEndTag(builder: PsiBuilder): Boolean {
        if (builder.tokenType != VocTypes.XML_END_TAG_START) return false
        val tagName = peekEndTagName(builder)
        return tagName == "style"
    }

    private fun parseStyleRule(builder: PsiBuilder) {
        val ruleMarker = builder.mark()
        parseStyleSelector(builder)
        if (builder.tokenType == VocTypes.LBRACE) {
            parseStyleBody(builder)
        }
        ruleMarker.done(VocTypes.STYLE_RULE)
    }

    private fun parseStyleSelector(builder: PsiBuilder) {
        val selectorMarker = builder.mark()
        while (builder.tokenType != null && builder.tokenType != VocTypes.LBRACE) {
            builder.advanceLexer()
        }
        selectorMarker.done(VocTypes.STYLE_SELECTOR)
    }

    private fun parseStyleBody(builder: PsiBuilder) {
        if (builder.tokenType != VocTypes.LBRACE) return
        val bodyMarker = builder.mark()
        builder.advanceLexer()

        while (builder.tokenType != null && builder.tokenType != VocTypes.RBRACE) {
            if (isStylePropertyStart(builder)) {
                parseStyleProperty(builder)
            } else if (builder.tokenType == VocTypes.LBRACE) {
                parseStyleRule(builder)
            } else {
                builder.advanceLexer()
            }
        }

        if (builder.tokenType == VocTypes.RBRACE) {
            builder.advanceLexer()
        }

        bodyMarker.done(VocTypes.STYLE_BODY)
    }

    private fun isStylePropertyStart(builder: PsiBuilder): Boolean {
        if (builder.tokenType != VocTypes.IDENTIFIER) return false
        val marker = builder.mark()
        builder.advanceLexer()
        val isProperty = builder.tokenType == VocTypes.COLON
        marker.rollbackTo()
        return isProperty
    }

    private fun parseStyleProperty(builder: PsiBuilder) {
        val propMarker = builder.mark()

        val nameMarker = builder.mark()
        if (builder.tokenType == VocTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        nameMarker.done(VocTypes.STYLE_PROPERTY_NAME)

        if (builder.tokenType == VocTypes.COLON) {
            builder.advanceLexer()
        }

        val valueMarker = builder.mark()
        while (builder.tokenType != null &&
            builder.tokenType != VocTypes.SEMICOLON &&
            builder.tokenType != VocTypes.RBRACE &&
            builder.tokenType != VocTypes.LBRACE
        ) {
            builder.advanceLexer()
        }
        valueMarker.done(VocTypes.STYLE_PROPERTY_VALUE)

        if (builder.tokenType == VocTypes.SEMICOLON) {
            builder.advanceLexer()
        }

        propMarker.done(VocTypes.STYLE_PROPERTY)
    }
}
