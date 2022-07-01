package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticAnimatorFileType
import com.github.game_gpt.language.GnosticAnimatorLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticAnimatorFile(view: FileViewProvider) : GnosticFile(view, GnosticAnimatorLanguage) {
    override fun getFileType(): FileType {
        return GnosticAnimatorFileType
    }
}
