package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticMeshLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticMeshFileType : LanguageFileType(GnosticMeshLanguage) {
    override fun getName(): String = "Gnostic Mesh"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Mesh Files"
    override fun getDefaultExtension(): String = "mesh"
    override fun getIcon(): Icon = GnosticIcons.VON_FILE
}
