package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticScriptFileType
import com.github.game_gpt.language.GnosticScriptLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticScriptFile(view: FileViewProvider) : GnosticFile(view, GnosticScriptLanguage) {
    override fun getFileType(): FileType {
        return GnosticScriptFileType
    }
}
