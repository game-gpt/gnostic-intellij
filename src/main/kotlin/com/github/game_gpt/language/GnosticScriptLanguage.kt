package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticScriptFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 脚本语言
 *
 * 用于编写游戏逻辑脚本的语言。
 * 继承自 GnosticLanguage，是 GnosticSchemaLanguage 和 GnosticShaderLanguage 的父语言。
 */
object GnosticScriptLanguage : Language(GnosticLanguage, "GnosticScript") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Script"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Script"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Script 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticScriptFileType
    }
}

