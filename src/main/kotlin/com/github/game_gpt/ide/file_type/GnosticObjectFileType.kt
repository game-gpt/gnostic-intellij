package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticObjectFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Object"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Object Files"
    override fun getDefaultExtension(): String = "von"
    override fun getIcon(): Icon = GnosticIcons.VON_FILE
}