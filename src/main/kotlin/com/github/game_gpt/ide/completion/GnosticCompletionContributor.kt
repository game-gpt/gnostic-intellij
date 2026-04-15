package com.github.game_gpt.ide.completion

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.ide.index.GnosticSymbolType
import com.github.game_gpt.ide.resolve.GnosticSymbolResolver
import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import javax.swing.Icon

/**
 * Gnostic 代码补全贡献者
 * 提供关键字补全和基于符号索引的类型名称补全
 */
class GnosticCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            psiElement(ValkyrieTypes.IDENTIFIER)
                .inside(psiElement(ValkyrieTypes.TYPE_REFERENCE)),
            GnosticTypeCompletionProvider()
        )

        extend(
            CompletionType.BASIC,
            psiElement(ValkyrieTypes.IDENTIFIER)
                .inside(psiElement(ValkyrieTypes.USING_DECLARATION)),
            GnosticNamespaceCompletionProvider()
        )

        extend(
            CompletionType.BASIC,
            psiElement(),
            GnosticKeywordCompletionProvider()
        )
    }
}

/**
 * 关键字补全提供者
 * 根据文件扩展名提供对应语言的关键字列表
 */
class GnosticKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val fileExtension = parameters.originalFile.virtualFile?.extension ?: return
        val keywords = getKeywordsForExtension(fileExtension)

        for (keyword in keywords) {
            result.addElement(LookupElementBuilder.create(keyword))
        }
    }

    companion object {
        private val VON_KEYWORDS = listOf("true", "false", "null")

        private val NOTEDOWN_KEYWORDS = listOf(
            "let", "include", "else", "true", "false", "DONE"
        )

        private val VALKYRIE_KEYWORDS = listOf(
            "namespace", "using", "class", "fn", "let", "const",
            "trait", "shader", "schema", "enum", "enums",
            "model", "service", "message",
            "if", "else", "loop", "while", "return", "break", "continue",
            "micro", "mezzo", "macro",
            "true", "false", "null",
            "in", "until"
        )

        private fun getKeywordsForExtension(extension: String): List<String> {
            return when (extension) {
                "script", "shader", "schema" -> VALKYRIE_KEYWORDS
                "von", "config", "meta", "mesh", "skeleton", "animation",
                "animator", "locale", "material", "prefab", "scene" -> VON_KEYWORDS
                "story" -> NOTEDOWN_KEYWORDS
                else -> emptyList()
            }
        }
    }
}

/**
 * 类型名称补全提供者
 * 基于符号索引提供当前上下文中可用的类型名称
 */
class GnosticTypeCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val project = parameters.originalFile.project
        val resolver = GnosticSymbolResolver(project)
        val position = parameters.position
        val symbols = resolver.getAvailableSymbols(position)

        for (symbol in symbols) {
            val icon = getIconForSymbolType(symbol.type)
            result.addElement(
                LookupElementBuilder.create(symbol.name)
                    .withIcon(icon)
                    .withTypeText(symbol.namespace)
                    .withTailText(" (${symbol.type.name.lowercase()})", true)
            )
        }
    }

    companion object {
        private fun getIconForSymbolType(type: GnosticSymbolType): Icon? {
            return when (type) {
                GnosticSymbolType.NAMESPACE -> GnosticIcons.Nodes.NAMESPACE
                GnosticSymbolType.CLASS -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.ENUM -> GnosticIcons.Nodes.ENUMS
                GnosticSymbolType.MODEL -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.SERVICE -> GnosticIcons.Nodes.METHOD
                GnosticSymbolType.MESSAGE -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.FUNCTION -> GnosticIcons.Nodes.METHOD
                GnosticSymbolType.VARIABLE -> GnosticIcons.Nodes.FIELD
                GnosticSymbolType.CONSTANT -> GnosticIcons.Nodes.PROPERTY
                GnosticSymbolType.SHADER -> GnosticIcons.Nodes.SHADER_NODE
            }
        }
    }
}

/**
 * 命名空间补全提供者
 * 在 using 语句后提供可用的命名空间名称
 */
class GnosticNamespaceCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val project = parameters.originalFile.project
        val resolver = GnosticSymbolResolver(project)
        val namespaces = resolver.getAllNamespaces()

        for (namespace in namespaces) {
            result.addElement(
                LookupElementBuilder.create(namespace)
                    .withIcon(GnosticIcons.Nodes.NAMESPACE)
                    .withTypeText("namespace")
            )
        }

        val position = parameters.position
        val symbols = resolver.getAvailableSymbols(position)
        for (symbol in symbols.distinctBy { it.namespace + "." + it.name }) {
            val qualifiedName = if (symbol.namespace.isNotEmpty()) {
                "${symbol.namespace}.${symbol.name}"
            } else {
                symbol.name
            }
            result.addElement(
                LookupElementBuilder.create(qualifiedName)
                    .withIcon(getIconForSymbolType(symbol.type))
                    .withTypeText(symbol.type.name.lowercase())
            )
        }
    }

    companion object {
        private fun getIconForSymbolType(type: GnosticSymbolType): Icon? {
            return when (type) {
                GnosticSymbolType.NAMESPACE -> GnosticIcons.Nodes.NAMESPACE
                GnosticSymbolType.CLASS -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.ENUM -> GnosticIcons.Nodes.ENUMS
                GnosticSymbolType.MODEL -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.SERVICE -> GnosticIcons.Nodes.METHOD
                GnosticSymbolType.MESSAGE -> GnosticIcons.Nodes.CLASS
                GnosticSymbolType.FUNCTION -> GnosticIcons.Nodes.METHOD
                GnosticSymbolType.VARIABLE -> GnosticIcons.Nodes.FIELD
                GnosticSymbolType.CONSTANT -> GnosticIcons.Nodes.PROPERTY
                GnosticSymbolType.SHADER -> GnosticIcons.Nodes.SHADER_NODE
            }
        }
    }
}
