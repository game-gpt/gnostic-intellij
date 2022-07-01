package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticObjectFileType
import com.github.game_gpt.language.GnosticObjectLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticObjectFile(view: FileViewProvider) : GnosticFile(view, GnosticObjectLanguage) {
    override fun getFileType(): FileType {
        return GnosticObjectFileType
    }
}
