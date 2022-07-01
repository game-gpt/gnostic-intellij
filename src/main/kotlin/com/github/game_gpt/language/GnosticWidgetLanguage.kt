package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticWidgetFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType

/**
 * Gnostic Widget 语言
 *
 * 用于定义编辑器 UI 组件的语言，基于 DOM 模型。
 * 用于 IDE 插件界面开发，与游戏运行时 UI（Prefab）体系完全不同。
 *
 * 继承自 GnosticLanguage。
 */
object GnosticWidgetLanguage : Language(GnosticLanguage, "GnosticWidget") {
    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Widget 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticWidgetFileType
    }
}