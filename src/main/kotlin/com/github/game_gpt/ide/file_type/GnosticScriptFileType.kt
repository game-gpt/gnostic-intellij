package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticScriptLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticScriptFileType : LanguageFileType(GnosticScriptLanguage) {
    override fun getName(): String = "Gnostic Script"
    override fun getDisplayName(): @Nls String {
        return super.getDisplayName()
    }

    override fun getDescription(): String = "Gnostic Script Files"
    override fun getDefaultExtension(): String = "script"
    override fun getIcon(): Icon = GnosticIcons.FILE
}


