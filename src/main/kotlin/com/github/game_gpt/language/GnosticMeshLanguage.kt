package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticMeshFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 网格语言
 *
 * 用于定义 3D 网格模型数据的语言。
 * 存储顶点、面、UV 等网格信息。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticMeshLanguage : Language(GnosticObjectLanguage, "GnosticMesh") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Mesh"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Mesh"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Mesh 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticMeshFileType
    }
}
