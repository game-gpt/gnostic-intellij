package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticConfigLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticConfigFileType : LanguageFileType(GnosticConfigLanguage) {
    override fun getName(): String = "GnosticConfig"
    override fun getDescription(): String = "Gnostic Config files"
    override fun getDefaultExtension(): String = "config"
    override fun getIcon(): Icon = GnosticIcons.FILE
}
