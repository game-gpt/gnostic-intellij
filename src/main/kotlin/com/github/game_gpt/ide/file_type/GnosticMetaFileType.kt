package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticMetaLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticMetaFileType : LanguageFileType(GnosticMetaLanguage) {
    override fun getName(): String = "Gnostic Meta"
    override fun getDisplayName(): @Nls String {
        return super.getDisplayName()
    }

    override fun getDescription(): String = "Gnostic Meta Files"
    override fun getDefaultExtension(): String = "meta"
    override fun getIcon(): Icon = GnosticIcons.META_FILE
}