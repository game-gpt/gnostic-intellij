package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticShaderFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 着色器语言
 *
 * 用于定义渲染着色器的语言。
 * 存储着色器代码、渲染管线配置等信息，定义游戏对象的视觉渲染效果。
 *
 * 继承自 GnosticScriptLanguage。
 */
object GnosticShaderLanguage : Language(GnosticScriptLanguage, "GnosticShader") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Shader"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Shader"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Shader 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticShaderFileType
    }
}