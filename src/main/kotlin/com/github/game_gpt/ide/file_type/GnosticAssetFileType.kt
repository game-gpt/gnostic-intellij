package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticAssetFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Asset"
    override fun getDescription(): String = "Gnostic Asset Files"
    override fun getDefaultExtension(): String = "asset"
    override fun getIcon(): Icon = GnosticIcons.ASSET
}
