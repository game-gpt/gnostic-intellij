package com.github.game_gpt.ide.intentions

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class GnosticLifecycleFunctionsIntentionFactory {
    fun createAction(): IntentionAction {
        return GnosticLifecycleFunctionsIntention()
    }
}
