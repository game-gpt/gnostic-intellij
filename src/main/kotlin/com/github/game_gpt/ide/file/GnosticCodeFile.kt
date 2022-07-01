package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticFileType
import com.github.game_gpt.language.GnosticLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticCodeFile(view: FileViewProvider) : GnosticFile(view, GnosticLanguage) {
    override fun getFileType(): FileType {
        return GnosticFileType
    }
}
