package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.config.ValkyrieLanguageConfig
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

class ValkyrieParser(private val config: ValkyrieLanguageConfig) : PsiParser, LightPsiParser {

    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val marker = builder.mark()
        while (builder.tokenType != null) {
            parseDeclaration(builder)
        }
        marker.done(root)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType?, builder: PsiBuilder?) {
        if (builder != null && root != null) {
            val marker = builder.mark()
            while (builder.tokenType != null) {
                parseDeclaration(builder)
            }
            marker.done(root)
        }
    }

    private fun parseDeclaration(builder: PsiBuilder) {
        when (builder.tokenType) {
            ValkyrieTypes.KEYWORD_NAMESPACE -> parseNamespace(builder)
            ValkyrieTypes.KEYWORD_USING -> parseUsing(builder)
            ValkyrieTypes.KEYWORD_CLASS -> parseClass(builder)
            ValkyrieTypes.KEYWORD_SHADER -> parseShader(builder)
            ValkyrieTypes.KEYWORD_TRAIT -> parseTrait(builder)
            ValkyrieTypes.KEYWORD_MICRO -> parseMicro(builder)
            ValkyrieTypes.KEYWORD_MEZZO -> parseMezzo(builder)
            ValkyrieTypes.KEYWORD_MACRO -> parseMacro(builder)
            ValkyrieTypes.KEYWORD_FN -> parseFunction(builder)
            ValkyrieTypes.KEYWORD_LET -> parseLet(builder)
            ValkyrieTypes.KEYWORD_CONST -> parseConst(builder)
            ValkyrieTypes.KEYWORD_SCHEMA -> parseSchema(builder)
            ValkyrieTypes.KEYWORD_ENUM -> parseEnum(builder)
            ValkyrieTypes.KEYWORD_ENUMS -> parseEnums(builder)
            ValkyrieTypes.KEYWORD_MODEL -> parseModel(builder)
            ValkyrieTypes.KEYWORD_SERVICE -> parseService(builder)
            ValkyrieTypes.KEYWORD_MESSAGE -> parseMessage(builder)
            ValkyrieTypes.KEYWORD_SYSTEM -> parseSystem(builder)
            else -> builder.advanceLexer()
        }
    }

    private fun parseNamespace(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifierPath(builder)
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.NAMESPACE_DECLARATION)
    }

    private fun parseUsing(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifierPath(builder)
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.USING_DECLARATION)
    }

    private fun parseClass(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseIdentifierPath(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.CLASS_DECLARATION)
    }

    private fun parseShader(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.KEYWORD_BY) {
            parseShaderKind(builder)
        }
        parseShaderBlock(builder)
        marker.done(ValkyrieTypes.SHADER_DECLARATION)
    }

    private fun parseShaderKind(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        marker.done(ValkyrieTypes.SHADER_KIND)
    }

    private fun parseShaderBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            when (builder.tokenType) {
                ValkyrieTypes.KEYWORD_RENDER_STATES -> parseRenderStatesBlock(builder)
                ValkyrieTypes.KEYWORD_VERTEX -> parseVertexFunction(builder)
                ValkyrieTypes.KEYWORD_FRAGMENT -> parseFragmentFunction(builder)
                ValkyrieTypes.KEYWORD_COMPUTE -> parseComputeFunction(builder)
                ValkyrieTypes.KEYWORD_UNIFORMS -> parseUniformsBlock(builder)
                ValkyrieTypes.KEYWORD_FALLBACK -> parseFallbackBlock(builder)
                ValkyrieTypes.IDENTIFIER -> parseShaderProperty(builder)
                else -> builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseShaderProperty(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        marker.done(ValkyrieTypes.SHADER_PROPERTY)
    }

    private fun parseRenderStatesBlock(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == ValkyrieTypes.LBRACE) {
            builder.advanceLexer()
            while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
                if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
                    parseConfigField(builder)
                } else {
                    builder.advanceLexer()
                }
            }
            if (builder.tokenType == ValkyrieTypes.RBRACE) {
                builder.advanceLexer()
            }
        }
        marker.done(ValkyrieTypes.RENDER_STATES_BLOCK)
    }

    private fun parseVertexFunction(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.VERTEX_FUNCTION)
    }

    private fun parseFragmentFunction(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.FRAGMENT_FUNCTION)
    }

    private fun parseComputeFunction(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseParameterList(builder)
        parseBlock(builder)
        marker.done(ValkyrieTypes.COMPUTE_FUNCTION)
    }

    private fun parseUniformsBlock(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == ValkyrieTypes.LBRACE) {
            builder.advanceLexer()
            while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
                if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
                    parseUniformField(builder)
                } else {
                    builder.advanceLexer()
                }
            }
            if (builder.tokenType == ValkyrieTypes.RBRACE) {
                builder.advanceLexer()
            }
        }
        marker.done(ValkyrieTypes.UNIFORMS_BLOCK)
    }

    private fun parseUniformField(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.UNIFORM_FIELD)
    }

    private fun parseFallbackBlock(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == ValkyrieTypes.LBRACE) {
            builder.advanceLexer()
            while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
                if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
                    parseConfigField(builder)
                } else {
                    builder.advanceLexer()
                }
            }
            if (builder.tokenType == ValkyrieTypes.RBRACE) {
                builder.advanceLexer()
            }
        }
        marker.done(ValkyrieTypes.FALLBACK_BLOCK)
    }

    private fun parseConfigField(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseExpression(builder)
        } else if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.FIELD_DECLARATION)
    }

    private fun parseTrait(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseBlock(builder)
        marker.done(ValkyrieTypes.TRAIT_DECLARATION)
    }

    private fun parseMicro(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.MICRO_DECLARATION)
    }

    private fun parseMezzo(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.MEZZO_DECLARATION)
    }

    private fun parseMacro(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.MACRO_DECLARATION)
    }

    private fun parseFunction(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.FUNCTION_DECLARATION)
    }

    private fun parseLet(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == ValkyrieTypes.KEYWORD_MUT) {
            builder.advanceLexer()
        }
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.LET_DECLARATION)
    }

    private fun parseConst(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.CONST_DECLARATION)
    }

    private fun parseSchema(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseSchemaBlock(builder)
        marker.done(ValkyrieTypes.SCHEMA_DECLARATION)
    }

    private fun parseSchemaBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            when (builder.tokenType) {
                ValkyrieTypes.KEYWORD_MODEL -> parseModel(builder)
                ValkyrieTypes.IDENTIFIER -> parseSchemaConfigField(builder)
                else -> builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseSchemaConfigField(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.SCHEMA_CONFIG_FIELD)
    }

    private fun parseEnum(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseEnumBlock(builder)
        marker.done(ValkyrieTypes.ENUM_DECLARATION)
    }

    private fun parseEnums(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseEnumBlock(builder)
        marker.done(ValkyrieTypes.ENUMS_DECLARATION)
    }

    private fun parseEnumBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
                parseEnumVariant(builder)
            } else {
                builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseEnumVariant(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.ENUM_VARIANT)
    }

    private fun parseModel(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseModelBlock(builder)
        marker.done(ValkyrieTypes.MODEL_DECLARATION)
    }

    private fun parseModelBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            when (builder.tokenType) {
                ValkyrieTypes.AT -> parseAnnotation(builder)
                ValkyrieTypes.IDENTIFIER -> parseModelField(builder)
                else -> builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseModelField(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.QUESTION) {
            builder.advanceLexer()
        }
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.MODEL_FIELD)
    }

    private fun parseService(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseServiceBlock(builder)
        marker.done(ValkyrieTypes.SERVICE_DECLARATION)
    }

    private fun parseServiceBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            when (builder.tokenType) {
                ValkyrieTypes.AT -> parseAnnotation(builder)
                ValkyrieTypes.IDENTIFIER -> parseServiceMethod(builder)
                else -> builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseServiceMethod(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        parseParameterList(builder)
        if (builder.tokenType == ValkyrieTypes.ARROW) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.SERVICE_METHOD)
    }

    private fun parseMessage(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        parseMessageBlock(builder)
        marker.done(ValkyrieTypes.MESSAGE_DECLARATION)
    }

    private fun parseMessageBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            when (builder.tokenType) {
                ValkyrieTypes.AT -> parseAnnotation(builder)
                ValkyrieTypes.IDENTIFIER -> parseMessageField(builder)
                else -> builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseMessageField(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
        if (builder.tokenType == ValkyrieTypes.QUESTION) {
            builder.advanceLexer()
        }
        if (builder.tokenType == ValkyrieTypes.EQUALS) {
            builder.advanceLexer()
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.MESSAGE_FIELD)
    }

    private fun parseSystem(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
        if (builder.tokenType == ValkyrieTypes.LBRACE) {
            builder.advanceLexer()
            while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
                parseStatement(builder)
            }
            if (builder.tokenType == ValkyrieTypes.RBRACE) {
                builder.advanceLexer()
            }
        }
        marker.done(ValkyrieTypes.CLASS_DECLARATION)
    }

    private fun parseAnnotation(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.LPAREN) {
            parseArgumentList(builder)
        }
        marker.done(ValkyrieTypes.ANNOTATION)
    }

    private fun parseBlock(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LBRACE) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RBRACE) {
            parseStatement(builder)
        }
        if (builder.tokenType == ValkyrieTypes.RBRACE) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.BLOCK)
    }

    private fun parseStatement(builder: PsiBuilder) {
        when (builder.tokenType) {
            ValkyrieTypes.KEYWORD_LET -> parseLet(builder)
            ValkyrieTypes.KEYWORD_CONST -> parseConst(builder)
            ValkyrieTypes.KEYWORD_IF -> parseIfStatement(builder)
            ValkyrieTypes.KEYWORD_LOOP -> parseLoopStatement(builder)
            ValkyrieTypes.KEYWORD_WHILE -> parseWhileStatement(builder)
            ValkyrieTypes.KEYWORD_RETURN -> parseReturnStatement(builder)
            ValkyrieTypes.KEYWORD_MICRO -> parseMicro(builder)
            ValkyrieTypes.KEYWORD_MEZZO -> parseMezzo(builder)
            ValkyrieTypes.KEYWORD_MACRO -> parseMacro(builder)
            ValkyrieTypes.KEYWORD_FN -> parseFunction(builder)
            ValkyrieTypes.KEYWORD_CONSTRUCTOR -> parseFunction(builder)
            else -> parseExpressionStatement(builder)
        }
    }

    private fun parseIfStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseExpression(builder)
        parseBlock(builder)
        if (builder.tokenType == ValkyrieTypes.KEYWORD_ELSE) {
            builder.advanceLexer()
            if (builder.tokenType == ValkyrieTypes.KEYWORD_IF) {
                parseIfStatement(builder)
            } else {
                parseBlock(builder)
            }
        }
        marker.done(ValkyrieTypes.IF_STATEMENT)
    }

    private fun parseLoopStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        // 跳过循环变量声明部分，直到遇到 in 关键字
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.KEYWORD_IN && builder.tokenType != ValkyrieTypes.LBRACE) {
            builder.advanceLexer()
        }
        if (builder.tokenType == ValkyrieTypes.KEYWORD_IN) {
            builder.advanceLexer()
            // 跳过迭代器表达式，直到遇到左大括号
            while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.LBRACE) {
                builder.advanceLexer()
            }
        }
        parseBlock(builder)
        marker.done(ValkyrieTypes.LOOP_STATEMENT)
    }

    private fun parseWhileStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        parseExpression(builder)
        parseBlock(builder)
        marker.done(ValkyrieTypes.WHILE_STATEMENT)
    }

    private fun parseReturnStatement(builder: PsiBuilder) {
        val marker = builder.mark()
        builder.advanceLexer()
        if (builder.tokenType != ValkyrieTypes.SEMICOLON && builder.tokenType != ValkyrieTypes.RBRACE) {
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.RETURN_STATEMENT)
    }

    private fun parseExpressionStatement(builder: PsiBuilder) {
        parseExpression(builder)
        if (builder.tokenType == ValkyrieTypes.SEMICOLON) {
            builder.advanceLexer()
        }
    }

    private fun parseParameterList(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LPAREN) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RPAREN) {
            if (builder.tokenType == ValkyrieTypes.COMMA) {
                builder.advanceLexer()
                continue
            }
            if (builder.tokenType == ValkyrieTypes.AT) {
                parseAnnotation(builder)
            }
            parseParameter(builder)
        }
        if (builder.tokenType == ValkyrieTypes.RPAREN) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.PARAMETER_LIST)
    }

    private fun parseParameter(builder: PsiBuilder) {
        parseIdentifier(builder)
        if (builder.tokenType == ValkyrieTypes.COLON) {
            builder.advanceLexer()
            parseTypeReference(builder)
        }
    }

    private fun parseArgumentList(builder: PsiBuilder) {
        if (builder.tokenType != ValkyrieTypes.LPAREN) return
        val marker = builder.mark()
        builder.advanceLexer()
        while (builder.tokenType != null && builder.tokenType != ValkyrieTypes.RPAREN) {
            if (builder.tokenType == ValkyrieTypes.COMMA) {
                builder.advanceLexer()
                continue
            }
            parseExpression(builder)
        }
        if (builder.tokenType == ValkyrieTypes.RPAREN) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.ARGUMENT_LIST)
    }

    private fun parseTypeReference(builder: PsiBuilder) {
        val marker = builder.mark()
        parseIdentifierPath(builder)
        if (builder.tokenType == ValkyrieTypes.LBRACK) {
            builder.advanceLexer()
            if (builder.tokenType == ValkyrieTypes.AND) {
                builder.advanceLexer()
            }
            parseTypeReference(builder)
            if (builder.tokenType == ValkyrieTypes.RBRACK) {
                builder.advanceLexer()
            }
        }
        if (builder.tokenType == ValkyrieTypes.QUESTION) {
            builder.advanceLexer()
        }
        marker.done(ValkyrieTypes.TYPE_REFERENCE)
    }

    private fun parseIdentifierPath(builder: PsiBuilder) {
        parseIdentifier(builder)
        while (builder.tokenType == ValkyrieTypes.DOT || builder.tokenType == ValkyrieTypes.DOUBLE_COLON) {
            builder.advanceLexer()
            parseIdentifier(builder)
        }
    }

    private fun parseIdentifier(builder: PsiBuilder) {
        if (builder.tokenType == ValkyrieTypes.IDENTIFIER) {
            builder.advanceLexer()
        }
    }

    private fun parseExpression(builder: PsiBuilder) {
        val marker = builder.mark()
        var count = 0
        while (builder.tokenType != null &&
            builder.tokenType != ValkyrieTypes.SEMICOLON &&
            builder.tokenType != ValkyrieTypes.RBRACE &&
            builder.tokenType != ValkyrieTypes.RPAREN &&
            builder.tokenType != ValkyrieTypes.RBRACK &&
            count < 1000 // 添加计数器，防止无限循环
        ) {
            builder.advanceLexer()
            count++
        }
        marker.done(ValkyrieTypes.EXPRESSION)
    }
}
