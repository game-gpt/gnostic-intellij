package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticConfigFileType
import com.github.game_gpt.language.GnosticConfigLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticConfigFile(view: FileViewProvider) : GnosticFile(view, GnosticConfigLanguage) {
    override fun getFileType(): FileType {
        return GnosticConfigFileType
    }
}
