package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticAssetFileType
import com.github.game_gpt.language.GnosticAssetLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticAssetFile(view: FileViewProvider) : GnosticFile(view, GnosticAssetLanguage) {
    override fun getFileType(): FileType {
        return GnosticAssetFileType
    }
}
