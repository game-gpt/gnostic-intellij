package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticAnimationLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticAnimationFileType : LanguageFileType(GnosticAnimationLanguage) {
    override fun getName(): String = "Gnostic Animation"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Animation Files"
    override fun getDefaultExtension(): String = "animation"
    override fun getIcon(): Icon = GnosticIcons.ANIMATION
}
