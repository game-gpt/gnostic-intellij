package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticMaterialLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticMaterialFileType : LanguageFileType(GnosticMaterialLanguage) {
    override fun getName(): String = "Gnostic Material"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Material Files"
    override fun getDefaultExtension(): String = "material"
    override fun getIcon(): Icon = GnosticIcons.MATERIAL_FILE
}
