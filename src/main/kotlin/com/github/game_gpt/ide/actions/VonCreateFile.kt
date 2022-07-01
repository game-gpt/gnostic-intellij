package com.github.game_gpt.ide.actions

import com.github.game_gpt.ide.file_type.GnosticObjectFileType
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class VonCreateFile : CreateFileFromTemplateAction(
    "VON File",
    "Create a new VON file",
    GnosticObjectFileType.icon
), DumbAware {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New VON File")
            .addKind("Empty VON File", GnosticObjectFileType.icon, "VON File")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create VON File"
    }
}
