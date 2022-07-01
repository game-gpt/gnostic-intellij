package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticBrushFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 笔刷语言
 *
 * 用于定义地形编辑笔刷的语言。
 * 支持地形绘制、雕刻等功能。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticBrushLanguage : Language(GnosticObjectLanguage, "GnosticBrush") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Brush"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Brush"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Brush 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticBrushFileType
    }
}
