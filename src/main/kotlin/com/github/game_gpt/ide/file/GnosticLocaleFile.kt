package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticLocaleFileType
import com.github.game_gpt.language.GnosticLocaleLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticLocaleFile(view: FileViewProvider) : GnosticFile(view, GnosticLocaleLanguage) {
    override fun getFileType(): FileType {
        return GnosticLocaleFileType
    }
}
