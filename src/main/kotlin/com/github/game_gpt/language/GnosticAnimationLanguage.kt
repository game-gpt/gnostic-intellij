package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticAnimationFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 动画语言
 *
 * 用于定义游戏动画的语言，存储关键帧、曲线、时间线和事件。
 * 支持变换动画、精灵动画、材质动画等多种动画类型。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticAnimationLanguage : Language(GnosticObjectLanguage, "GnosticAnimation") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Animation"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Animation"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Animation 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticAnimationFileType
    }
}
