package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticMixerFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 混合器语言
 *
 * 用于定义音频混合器的语言。
 * 管理音频轨道、效果器和混音设置。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticMixerLanguage : Language(GnosticObjectLanguage, "GnosticMixer") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Mixer"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Mixer"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Mixer 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticMixerFileType
    }
}
