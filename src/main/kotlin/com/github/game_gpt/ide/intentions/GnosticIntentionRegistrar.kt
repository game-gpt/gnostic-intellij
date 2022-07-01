package com.github.game_gpt.ide.intentions

import com.intellij.codeInsight.intention.IntentionManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
/**
 * GG 引擎意图注册器
 */
class GnosticIntentionRegistrar {
    companion object {
        fun registerIntentions() {
            ApplicationManager.getApplication().invokeLater {
                val intentionManager = IntentionManager.getInstance()
                
                // 注册生命周期函数生成意图
                val intention = GnosticLifecycleFunctionsIntention()
                intentionManager.addAction(intention)
            }
        }
    }
}
