package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticPrefabFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 预制体语言
 *
 * 用于定义可重用游戏对象模板的语言。
 * 存储游戏对象的层次结构、组件和属性，支持变体机制实现差异化版本。
 * 可用于角色、道具、环境元素等游戏对象的模板化。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticPrefabLanguage : Language(GnosticObjectLanguage, "GnosticPrefab") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Prefab"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Prefab"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Prefab 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticPrefabFileType
    }
}
