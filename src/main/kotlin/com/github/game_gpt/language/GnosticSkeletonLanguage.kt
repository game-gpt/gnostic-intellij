package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticSkeletonFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 骨骼语言
 *
 * 用于定义骨骼绑定数据的语言。
 * 存储骨骼层次结构、绑定姿态等信息，用于角色动画和蒙皮网格。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticSkeletonLanguage : Language(GnosticObjectLanguage, "GnosticSkeleton") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Skeleton"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Skeleton"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Skeleton 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticSkeletonFileType
    }
}
