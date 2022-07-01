package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticMetaFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 元数据语言
 *
 * 用于定义资源元数据的语言。
 * 存储资源的 GUID、依赖关系、引用关系等元信息。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticMetaLanguage : Language(GnosticObjectLanguage, "GnosticMeta") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Meta"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Meta"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Meta 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticMetaFileType
    }
}