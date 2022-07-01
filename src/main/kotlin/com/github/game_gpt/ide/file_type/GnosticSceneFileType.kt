package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticSceneLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticSceneFileType : LanguageFileType(GnosticSceneLanguage) {
    override fun getName(): String = "Gnostic Scene"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Scene Files"
    override fun getDefaultExtension(): String = "scene"
    override fun getIcon(): Icon = GnosticIcons.SCENE
}
