package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticWidgetLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticWidgetFileType : LanguageFileType(GnosticWidgetLanguage) {
    override fun getName(): String = "Widget"
    override fun getDescription(): String = "Widget UI files"
    override fun getDefaultExtension(): String = "widget"
    override fun getIcon(): Icon = GnosticIcons.WIDGET

}
