package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticAssetFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 资产语言
 *
 * 用于定义通用资产配置的语言。
 * 可用于自定义资产类型的配置文件。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticAssetLanguage : Language(GnosticObjectLanguage, "GnosticAsset") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Asset"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Asset"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Asset 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticAssetFileType
    }
}
