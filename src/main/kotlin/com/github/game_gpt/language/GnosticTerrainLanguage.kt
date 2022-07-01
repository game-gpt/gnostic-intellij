package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticTerrainFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 地形语言
 *
 * 用于定义地形数据的语言。
 * 存储高度图、纹理层、植被分布等地形信息。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticTerrainLanguage : Language(GnosticObjectLanguage, "GnosticTerrain") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Terrain"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Terrain"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Terrain 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticTerrainFileType
    }
}
