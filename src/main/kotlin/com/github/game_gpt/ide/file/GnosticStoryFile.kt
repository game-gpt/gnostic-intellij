package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticStoryFileType
import com.github.game_gpt.language.GnosticStoryLanguage
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

/**
 * Gnostic Story 文件的 PSI 表示
 *
 * 使用 Notedown 语言作为底层语言，关联 .story 文件扩展名。
 */
class GnosticStoryFile(view: FileViewProvider) : GnosticFile(view, GnosticStoryLanguage) {
    /**
     * 获取文件类型
     *
     * @return Gnostic Story 文件类型
     */
    override fun getFileType(): FileType {
        return GnosticStoryFileType
    }
}
