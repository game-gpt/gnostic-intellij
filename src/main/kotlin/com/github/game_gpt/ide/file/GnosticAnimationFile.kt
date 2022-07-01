package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticAnimationFileType
import com.github.game_gpt.language.GnosticAnimationLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticAnimationFile(view: FileViewProvider) : GnosticFile(view, GnosticAnimationLanguage) {
    override fun getFileType(): FileType {
        return GnosticAnimationFileType
    }
}
