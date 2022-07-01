package com.github.game_gpt.ide.intentions

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.psi.PsiElement

/**
 * Gnostic 意图工厂
 * 用于注册代码意图操作
 */
class GnosticIntentionFactory {
    companion object {
        /**
         * 创建意图操作
         * @param element Psi元素
         * @return 意图操作列表
         */
        fun createIntentions(element: PsiElement): List<IntentionAction> {
            val intentions = mutableListOf<IntentionAction>()
            intentions.add(GnosticIntentionAction())
            // 可以添加更多的意图操作
            return intentions
        }
    }
}
