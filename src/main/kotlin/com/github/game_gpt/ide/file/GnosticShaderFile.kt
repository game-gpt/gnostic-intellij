package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticShaderFileType
import com.github.game_gpt.language.GnosticShaderLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticShaderFile(view: FileViewProvider) : GnosticFile(view, GnosticShaderLanguage) {
    override fun getFileType(): FileType {
        return GnosticShaderFileType
    }
}
