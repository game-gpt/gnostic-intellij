package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticTerrainFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Terrain"
    override fun getDescription(): String = "Gnostic Terrain Files"
    override fun getDefaultExtension(): String = "terrain"
    override fun getIcon(): Icon = GnosticIcons.TERRAIN
}
