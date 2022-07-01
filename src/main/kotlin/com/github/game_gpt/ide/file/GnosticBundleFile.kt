package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticBundleFileType
import com.github.game_gpt.language.GnosticBundleLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticBundleFile(view: FileViewProvider) : GnosticFile(view, GnosticBundleLanguage) {
    override fun getFileType(): FileType {
        return GnosticBundleFileType
    }
}
