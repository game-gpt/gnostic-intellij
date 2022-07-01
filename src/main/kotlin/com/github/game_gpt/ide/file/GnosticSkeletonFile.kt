package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticSkeletonFileType
import com.github.game_gpt.language.GnosticSkeletonLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class GnosticSkeletonFile(view: FileViewProvider) : GnosticFile(view, GnosticSkeletonLanguage) {
    override fun getFileType(): FileType {
        return GnosticSkeletonFileType
    }
}
