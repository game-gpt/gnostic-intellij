package com.github.game_gpt.ide.file_type

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.GnosticSkeletonLanguage
import com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.annotations.Nls
import javax.swing.Icon

object GnosticSkeletonFileType : LanguageFileType(GnosticSkeletonLanguage) {
    override fun getName(): String = "Gnostic Skeleton"
    override fun getDisplayName(): @Nls String = super.getDisplayName()
    override fun getDescription(): String = "Gnostic Skeleton Files"
    override fun getDefaultExtension(): String = "skeleton"
    override fun getIcon(): Icon = GnosticIcons.VON_FILE
}
