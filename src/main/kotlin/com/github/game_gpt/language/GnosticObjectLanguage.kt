package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticObjectFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 对象数据语言
 *
 * 基于 VON (Valkyrie Object Notation) 格式的数据序列化语言。
 * 用于存储游戏对象数据，如预制体、场景、材质、动画等。
 *
 * 继承自 GnosticLanguage，是大多数游戏资源语言的父语言。
 */
object GnosticObjectLanguage : Language(GnosticLanguage, "GnosticObject") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Object"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Object"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Object 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticObjectFileType
    }
}

