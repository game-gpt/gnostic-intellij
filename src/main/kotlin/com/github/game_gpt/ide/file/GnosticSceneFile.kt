package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticSceneFileType
import com.github.game_gpt.language.GnosticSceneLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticSceneFile(view: FileViewProvider) : GnosticFile(view, GnosticSceneLanguage) {
    override fun getFileType(): FileType {
        return GnosticSceneFileType
    }
}
