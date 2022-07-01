package com.github.game_gpt.ide.actions

import com.github.game_gpt.ide.file_type.GnosticWidgetFileType
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

class WidgetCreateFile : CreateFileFromTemplateAction(
    "Widget File",
    "Create a new Widget UI file",
    GnosticWidgetFileType.icon
), DumbAware {
    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle("New Widget File")
            .addKind("Empty Widget File", GnosticWidgetFileType.icon, "Widget File")
    }

    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return "Create Widget File"
    }
}
