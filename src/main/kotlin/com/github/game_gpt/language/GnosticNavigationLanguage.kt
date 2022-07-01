package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticNavigationFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 导航语言
 *
 * 用于定义导航网格的语言。
 * 存储寻路数据，支持 AI 导航和路径规划。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticNavigationLanguage : Language(GnosticObjectLanguage, "GnosticNavigation") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Navigation"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Navigation"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Navigation 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticNavigationFileType
    }
}
