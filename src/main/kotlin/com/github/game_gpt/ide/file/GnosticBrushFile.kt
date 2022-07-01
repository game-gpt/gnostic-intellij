package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticBrushFileType
import com.github.game_gpt.language.GnosticBrushLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticBrushFile(view: FileViewProvider) : GnosticFile(view, GnosticBrushLanguage) {
    override fun getFileType(): FileType {
        return GnosticBrushFileType
    }
}
