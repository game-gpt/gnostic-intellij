package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticSchemaFileType
import com.github.game_gpt.language.GnosticSchemaLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticSchemaFile(view: FileViewProvider) : GnosticFile(view, GnosticSchemaLanguage) {
    override fun getFileType(): FileType {
        return GnosticSchemaFileType
    }
}
