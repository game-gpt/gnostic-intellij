package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticStoryFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Story 语言（引擎中的表现）
 *
 * 用于定义 Galgame（视觉小说）脚本的语言，底层使用 Notedown 语言。
 * 存储剧情节点、对话、分支等信息，用于视觉小说等游戏类型。
 *
 * 继承自 GnosticLanguage，在 Gnostic 体系中的 ID 为 "GnosticStory"。
 */
object GnosticStoryLanguage : Language(GnosticLanguage, "GnosticStory") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Story"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Story"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Story 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticStoryFileType
    }
}
