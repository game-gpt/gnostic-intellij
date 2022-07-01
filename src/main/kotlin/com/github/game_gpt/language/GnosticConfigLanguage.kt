package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticConfigFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 配置语言
 *
 * 用于定义游戏配置的语言。
 * 存储游戏设置、参数等配置数据。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticConfigLanguage : Language(GnosticObjectLanguage, "GnosticConfig") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Config"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Config"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Config 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticConfigFileType
    }
}