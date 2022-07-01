package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticBrushFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Brush"
    override fun getDescription(): String = "Gnostic Brush Files"
    override fun getDefaultExtension(): String = "brush"
    override fun getIcon(): Icon = GnosticIcons.BRUSH
}
