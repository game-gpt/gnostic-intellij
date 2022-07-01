package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticSchemaLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticSchemaFileType : LanguageFileType(GnosticSchemaLanguage) {

    override fun getName(): String = "Gnostic Schema"
    override fun getDisplayName(): @Nls String {
        return super.getDisplayName()
    }

    override fun getDescription(): String = "Gnostic Schema Files"
    override fun getDefaultExtension(): String = "schema"
    override fun getIcon(): Icon = GnosticIcons.SCHEMA
}