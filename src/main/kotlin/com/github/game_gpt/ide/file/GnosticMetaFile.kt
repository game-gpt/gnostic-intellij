package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticMetaFileType
import com.github.game_gpt.language.GnosticMetaLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticMetaFile(view: FileViewProvider) : GnosticFile(view, GnosticMetaLanguage) {
    override fun getFileType(): FileType {
        return GnosticMetaFileType
    }
}
