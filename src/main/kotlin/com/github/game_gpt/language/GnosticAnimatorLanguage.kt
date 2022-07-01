package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticAnimatorFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

/**
 * Gnostic 动画控制器语言
 *
 * 用于定义动画状态机和动画切换逻辑的语言。
 * 管理多个动画之间的过渡和混合。
 *
 * 继承自 GnosticObjectLanguage。
 */
object GnosticAnimatorLanguage : Language(GnosticObjectLanguage, "GnosticAnimator") {
    /**
     * 获取语言显示名称
     *
     * @return 语言显示名称 "Gnostic Animator"
     */
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Animator"
    }

    /**
     * 获取关联的文件类型
     *
     * @return Gnostic Animator 文件类型
     */
    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticAnimatorFileType
    }
}
