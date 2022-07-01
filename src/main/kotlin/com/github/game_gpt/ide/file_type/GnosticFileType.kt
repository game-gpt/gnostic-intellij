package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticFileType : LanguageFileType(GnosticLanguage) {
    override fun getName(): String = "Gnostic Code"
    override fun getDescription(): String = "Gnostic Code files"
    override fun getDefaultExtension(): String = "gnostic"
    override fun getIcon(): Icon = GnosticIcons.FILE
}

