package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticLocaleLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticLocaleFileType : LanguageFileType(GnosticLocaleLanguage) {
    override fun getName(): String = "Gnostic Locale"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Locale Files"
    override fun getDefaultExtension(): String = "locale"
    override fun getIcon(): Icon = GnosticIcons.VON_FILE
}
