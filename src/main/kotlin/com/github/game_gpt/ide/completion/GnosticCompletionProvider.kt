package com.github.game_gpt.ide.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class GnosticCompletionProvider : CompletionProvider<CompletionParameters>() {
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

    private fun getKeywordsForExtension(extension: String): List<String> {
        return when (extension) {
            "script", "shader", "schema" -> VALKYRIE_KEYWORDS
            "von", "config", "meta", "mesh", "skeleton", "animation",
            "animator", "locale", "material", "prefab", "scene" -> VON_KEYWORDS
            "story" -> NOTEDOWN_KEYWORDS
            else -> emptyList()
        }
    }

    companion object {
        /**
         * VON 格式关键字列表
         */
        private val VON_KEYWORDS = listOf("true", "false", "null")

        /**
         * Notedown 格式关键字列表
         */
        private val NOTEDOWN_KEYWORDS = listOf(
            "let", "include", "else", "true", "false", "DONE"
        )

        /**
         * Valkyrie 脚本关键字列表
         */
        private val VALKYRIE_KEYWORDS = listOf(
            "namespace", "using", "class", "fn", "let", "const",
            "trait", "shader", "schema", "enum",
            "if", "else", "loop", "while", "return", "break", "continue",
            "micro", "mezzo", "macro",
            "true", "false", "null",
            "in", "until"
        )
    }
}
