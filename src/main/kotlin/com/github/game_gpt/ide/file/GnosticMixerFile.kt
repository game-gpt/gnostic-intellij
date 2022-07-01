package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticMixerFileType
import com.github.game_gpt.language.GnosticMixerLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticMixerFile(view: FileViewProvider) : GnosticFile(view, GnosticMixerLanguage) {
    override fun getFileType(): FileType {
        return GnosticMixerFileType
    }
}
