package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticPrefabLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticPrefabFileType : LanguageFileType(GnosticPrefabLanguage) {
    override fun getName(): String = "Gnostic Prefab"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Prefab Files"
    override fun getDefaultExtension(): String = "prefab"
    override fun getIcon(): Icon = GnosticIcons.PREFAB_FILE
}
