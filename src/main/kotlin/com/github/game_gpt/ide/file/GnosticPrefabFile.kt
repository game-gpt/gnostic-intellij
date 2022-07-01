package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticPrefabFileType
import com.github.game_gpt.language.GnosticPrefabLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticPrefabFile(view: FileViewProvider) : GnosticFile(view, GnosticPrefabLanguage) {
    override fun getFileType(): FileType {
        return GnosticPrefabFileType
    }
}
