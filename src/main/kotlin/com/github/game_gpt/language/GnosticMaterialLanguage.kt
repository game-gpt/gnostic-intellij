package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticMaterialFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 材质语言
 *
 * 用于定义游戏材质的语言，存储材质属性、着色器引用、纹理和渲染状态。
 * 支持 PBR、Unlit、Phong 等多种材质类型，可通过变体机制实现材质差异化。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticMaterialLanguage : Language(GnosticObjectLanguage, "GnosticMaterial") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Material"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Material"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Material 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticMaterialFileType
    }
}
