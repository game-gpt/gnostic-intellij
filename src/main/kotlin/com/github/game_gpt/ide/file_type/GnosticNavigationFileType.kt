package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticNavigationFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Navigation"
    override fun getDescription(): String = "Gnostic Navigation Files"
    override fun getDefaultExtension(): String = "navigation"
    override fun getIcon(): Icon = GnosticIcons.NAVIGATION
}
