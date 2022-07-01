package com.github.game_gpt.ide.icons

import com.intellij.ide.IconProvider
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import javax.swing.Icon

class GnosticIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        val psiFile = element.containingFile ?: return null
        val virtualFile = psiFile.virtualFile ?: return null

        return getIconByFile(virtualFile)
    }

    fun getIconByFile(file: VirtualFile): Icon? {
        val fileName = file.name.lowercase()
        return when {
            fileName.endsWith(".animation") -> GnosticIcons.ANIMATION
            fileName.endsWith(".config") -> GnosticIcons.CONFIG
            fileName.endsWith(".locale") -> GnosticIcons.LOCALE
            fileName.endsWith(".material") -> GnosticIcons.MATERIAL_FILE
            fileName.endsWith(".meta") -> GnosticIcons.META_FILE
            fileName.endsWith(".prefab") -> GnosticIcons.PREFAB_FILE
            fileName.endsWith(".scene") -> GnosticIcons.SCENE
            fileName.endsWith(".schema") -> GnosticIcons.SCHEMA
            fileName.endsWith(".script") -> GnosticIcons.SCRIPT
            fileName.endsWith(".shader") -> GnosticIcons.SHADER
            fileName.endsWith(".story") -> GnosticIcons.STORY
            fileName.endsWith(".von") -> GnosticIcons.VON_FILE
            fileName.endsWith(".widget") -> GnosticIcons.WIDGET

            fileName.endsWith(".asset") -> GnosticIcons.ASSET
            fileName.endsWith(".mixer") -> GnosticIcons.MIXER
            fileName.endsWith(".brush") -> GnosticIcons.BRUSH
            fileName.endsWith(".bundle") -> GnosticIcons.BUNDLE
            fileName.endsWith(".terrain") -> GnosticIcons.TERRAIN
            fileName.endsWith(".navigation") -> GnosticIcons.NAVIGATION

            else -> null
        }
    }

}
