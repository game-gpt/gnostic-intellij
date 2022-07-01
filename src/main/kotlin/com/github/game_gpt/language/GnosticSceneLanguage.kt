package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticSceneFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 场景语言
 *
 * 用于定义游戏场景的语言。
 * 存储场景结构、实体布局、环境设置、预制体实例等信息。
 * 是游戏世界的基础构建块，用于组织和管理游戏中的各种元素。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticSceneLanguage : Language(GnosticObjectLanguage, "GnosticScene") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Scene"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Scene"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Scene 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticSceneFileType
    }
}
