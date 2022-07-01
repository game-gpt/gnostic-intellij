package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticShaderLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticShaderFileType : LanguageFileType(GnosticShaderLanguage) {

    override fun getName(): String = "Gnostic Shader"
    override fun getDisplayName(): @Nls String {
        return super.getDisplayName()
    }

    override fun getDescription(): String = "Gnostic Shader Files"
    override fun getDefaultExtension(): String = "shader"
    override fun getIcon(): Icon = GnosticIcons.SHADER
}

