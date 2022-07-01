package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticNavigationFileType
import com.github.game_gpt.language.GnosticNavigationLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticNavigationFile(view: FileViewProvider) : GnosticFile(view, GnosticNavigationLanguage) {
    override fun getFileType(): FileType {
        return GnosticNavigationFileType
    }
}
