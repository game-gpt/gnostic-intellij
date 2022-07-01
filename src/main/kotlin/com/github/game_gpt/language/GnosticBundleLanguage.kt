package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticBundleFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 资源包语言
 *
 * 用于定义资源包配置的语言。
 * 用于打包和分发游戏资源。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticBundleLanguage : Language(GnosticObjectLanguage, "GnosticBundle") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Bundle"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Bundle"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Bundle 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticBundleFileType
    }
}
