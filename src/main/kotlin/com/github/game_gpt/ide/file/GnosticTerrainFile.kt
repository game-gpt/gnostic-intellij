package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticTerrainFileType
import com.github.game_gpt.language.GnosticTerrainLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticTerrainFile(view: FileViewProvider) : GnosticFile(view, GnosticTerrainLanguage) {
    override fun getFileType(): FileType {
        return GnosticTerrainFileType
    }
}
