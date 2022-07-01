package com.github.game_gpt.language.parser

import com.github.game_gpt.language.types.VonTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.LightPsiParser
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

class VonParser : PsiParser, LightPsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val marker = builder.mark()
        parseDocument(builder)
        marker.done(root)
        return builder.treeBuilt
    }

    override fun parseLight(root: IElementType?, builder: PsiBuilder?) {
        if (builder != null && root != null) {
            val marker = builder.mark()
            parseDocument(builder)
            marker.done(root)
        }
    }

    private fun parseDocument(builder: PsiBuilder) {
        while (builder.tokenType != null) {
            when (builder.tokenType) {
                VonTypes.IDENTIFIER -> {
                    if (lookaheadIsColon(builder)) {
                        parseKeyValuePair(builder)
                    } else {
                        parseTypedObject(builder)
                    }
                }
                VonTypes.KEYWORD_TRUE, VonTypes.KEYWORD_FALSE, VonTypes.KEYWORD_NULL -> {
                    if (lookaheadIsColon(builder)) {
                        parseKeyValuePair(builder)
                    } else {
                        builder.advanceLexer()
                    }
                }
                VonTypes.BRACE_L -> parseDict(builder)
                else -> builder.advanceLexer()
            }
        }
    }

    private fun lookaheadIsColon(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        builder.advanceLexer()
        val isColon = builder.tokenType == VonTypes.COLON
        marker.rollbackTo()
        return isColon
    }

    private fun lookaheadIsBrace(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        builder.advanceLexer()
        val isBrace = builder.tokenType == VonTypes.BRACE_L
        marker.rollbackTo()
        return isBrace
    }

    private fun parseTypedObject(builder: PsiBuilder) {
        val objectMarker = builder.mark()
        builder.advanceLexer()

        if (builder.tokenType == VonTypes.BRACE_L) {
            builder.advanceLexer()
            parseObjectContent(builder)
            if (builder.tokenType == VonTypes.BRACE_R) {
                builder.advanceLexer()
            } else {
                builder.error("Expected closing brace '}'")
            }
        } else {
            builder.error("Expected opening brace '{'")
        }

        objectMarker.done(VonTypes.VON_DICT)
    }

    private fun parseObjectContent(builder: PsiBuilder) {
        while (builder.tokenType != null && builder.tokenType != VonTypes.BRACE_R) {
            when (builder.tokenType) {
                VonTypes.IDENTIFIER,
                VonTypes.KEYWORD_TRUE,
                VonTypes.KEYWORD_FALSE,
                VonTypes.KEYWORD_NULL -> parseKeyValuePair(builder)
                else -> builder.advanceLexer()
            }
        }
    }

    private fun parseKeyValuePair(builder: PsiBuilder) {
        val pairMarker = builder.mark()
        builder.advanceLexer()

        if (builder.tokenType == VonTypes.COLON) {
            builder.advanceLexer()
            parseValue(builder)
            if (builder.tokenType == VonTypes.COMMA) {
                builder.advanceLexer()
            }
        } else {
            builder.error("Expected colon ':'")
        }

        pairMarker.done(VonTypes.VON_PAIR)
    }

    private fun parseValue(builder: PsiBuilder) {
        when (builder.tokenType) {
            VonTypes.LITERAL_STRING, VonTypes.LITERAL_NUMBER, VonTypes.KEYWORD_TRUE, VonTypes.KEYWORD_FALSE, VonTypes.KEYWORD_NULL -> {
                builder.advanceLexer()
            }
            VonTypes.BRACE_L -> {
                parseDict(builder)
            }
            VonTypes.BARACK_L -> {
                parseList(builder)
            }
            VonTypes.IDENTIFIER -> {
                if (lookaheadIsBrace(builder)) {
                    parseTypedObject(builder)
                } else {
                    builder.advanceLexer()
                }
            }
            else -> {
                builder.advanceLexer()
            }
        }
    }

    private fun parseDict(builder: PsiBuilder) {
        val dictMarker = builder.mark()
        builder.advanceLexer()

        parseObjectContent(builder)

        if (builder.tokenType == VonTypes.BRACE_R) {
            builder.advanceLexer()
        } else {
            builder.error("Expected closing brace '}'")
        }

        dictMarker.done(VonTypes.VON_DICT)
    }

    private fun parseList(builder: PsiBuilder) {
        val listMarker = builder.mark()
        builder.advanceLexer()

        while (builder.tokenType != null && builder.tokenType != VonTypes.BARACK_R) {
            parseValue(builder)
            if (builder.tokenType == VonTypes.COMMA) {
                builder.advanceLexer()
            }
        }

        if (builder.tokenType == VonTypes.BARACK_R) {
            builder.advanceLexer()
        } else {
            builder.error("Expected closing bracket ']'")
        }

        listMarker.done(VonTypes.VON_LIST)
    }

}
