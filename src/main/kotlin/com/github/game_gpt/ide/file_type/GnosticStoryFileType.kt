package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticStoryLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

/**
 * Gnostic Story 文件类型
 *
 * 关联 .story 文件扩展名到 Notedown 语言。
 * 文件类型名称使用 "Story"，但底层语言为 Notedown。
 */
object GnosticStoryFileType : LanguageFileType(GnosticStoryLanguage) {
    /**
     * 获取文件类型名称
     *
     * @return 文件类型名称 "Gnostic Story"
     */
    override fun getName(): String = "Gnostic Story"

    /**
     * 获取文件类型显示名称
     *
     * @return 文件类型显示名称
     */
    override fun getDisplayName(): @Nls String = super.getDisplayName()

    /**
     * 获取文件类型描述
     *
     * @return 文件类型描述 "Gnostic Story Files"
     */
    override fun getDescription(): String = "Gnostic Story Files"

    /**
     * 获取默认文件扩展名
     *
     * @return 默认文件扩展名 "story"
     */
    override fun getDefaultExtension(): String = "story"

    /**
     * 获取文件图标
     *
     * @return Story 文件图标
     */
    override fun getIcon(): Icon = GnosticIcons.STORY
}
