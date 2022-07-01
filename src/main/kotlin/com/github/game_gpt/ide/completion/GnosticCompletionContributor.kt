package com.github.game_gpt.ide.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns

/**
 * Gnostic 代码补全贡献者
 * 用于提供代码补全功能
 */
class GnosticCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            GnosticCompletionProvider(),
        )
    }
}
