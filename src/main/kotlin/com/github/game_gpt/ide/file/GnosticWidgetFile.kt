package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticWidgetFileType
import com.github.game_gpt.language.GnosticWidgetLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticWidgetFile(view: FileViewProvider) : GnosticFile(view, GnosticWidgetLanguage) {
    override fun getFileType(): FileType {
        return GnosticWidgetFileType
    }
}
