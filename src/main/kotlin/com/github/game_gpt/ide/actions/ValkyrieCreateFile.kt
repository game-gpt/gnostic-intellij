package com.github.game_gpt.ide.actions

import com.github.game_gpt.ide.file_type.GnosticScriptFileType
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class ValkyrieCreateFile : CreateFileFromTemplateAction(
    "Valkyrie File",
    "Create a new Valkyrie script file",
    GnosticScriptFileType.getIcon()
), DumbAware {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New Valkyrie File")
            .addKind("Empty Valkyrie File", GnosticScriptFileType.getIcon(), "Valkyrie File")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create Valkyrie File"
    }
}
