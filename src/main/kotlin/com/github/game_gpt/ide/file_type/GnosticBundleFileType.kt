package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticBundleFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Bundle"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Bundle Files"
    override fun getDefaultExtension(): String = "bundle"
    override fun getIcon(): Icon = GnosticIcons.BUNDLE
}
