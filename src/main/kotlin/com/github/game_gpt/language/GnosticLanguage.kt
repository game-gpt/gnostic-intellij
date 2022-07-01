package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 根语言
 *
 * 所有 Gnostic 语言的基类，定义了语言的基本标识。
 * 子语言包括 GnosticObjectLanguage、GnosticScriptLanguage 和 GnosticWidgetLanguage。
 */
object GnosticLanguage : Language("Gnostic") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticFileType
    }
}