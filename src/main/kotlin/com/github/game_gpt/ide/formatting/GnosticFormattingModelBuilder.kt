package com.github.game_gpt.ide.formatting

import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder

/**
 * Gnostic 格式化模型构建器
 * 用于支持代码格式化功能
 */
class GnosticFormattingModelBuilder : FormattingModelBuilder {
    /**
     * Creates a formatting model for the given formatting context.
     *
     * @param formattingContext The context containing information about the code to format
     * @return A new GnosticFormattingModel instance configured with the AST node and document
     */
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val node = formattingContext.node
        val document = formattingContext.containingFile.viewProvider.document
            ?: throw IllegalStateException("Document is null")
        return GnosticFormattingModel(node, document)
    }
}

