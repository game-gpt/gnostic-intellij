package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object GnosticMixerFileType : LanguageFileType(GnosticObjectLanguage) {
    override fun getName(): String = "Gnostic Mixer"
    override fun getDescription(): String = "Gnostic Mixer Files"
    override fun getDefaultExtension(): String = "mixer"
    override fun getIcon(): Icon = GnosticIcons.MIXER
}
