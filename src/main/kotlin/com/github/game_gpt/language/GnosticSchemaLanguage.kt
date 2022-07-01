package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticSchemaFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 模式语言
 *
 * 用于定义数据模式/结构的语言。
 * 用于验证数据结构，确保数据符合预期格式。
 *
 * 继承自 GnosticScriptLanguage。
 */
object GnosticSchemaLanguage : Language(GnosticScriptLanguage, "GnosticSchema") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Schema"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Schema"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Schema 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticSchemaFileType
    }
}