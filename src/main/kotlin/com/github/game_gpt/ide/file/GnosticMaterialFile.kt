package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticMaterialFileType
import com.github.game_gpt.language.GnosticMaterialLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticMaterialFile(view: FileViewProvider) : GnosticFile(view, GnosticMaterialLanguage) {
    override fun getFileType(): FileType {
        return GnosticMaterialFileType
    }
}
