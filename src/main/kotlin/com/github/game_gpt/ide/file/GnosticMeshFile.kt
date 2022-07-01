package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticMeshFileType
import com.github.game_gpt.language.GnosticMeshLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticMeshFile(view: FileViewProvider) : GnosticFile(view, GnosticMeshLanguage) {
    override fun getFileType(): FileType {
        return GnosticMeshFileType
    }
}
