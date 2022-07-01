package com.github.game_gpt.ide.structure

import com.github.game_gpt.ide.file.GnosticFile
import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.psi.PsiFile

class GnosticStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        if (psiFile !is GnosticFile) return null
        return GnosticStructureViewBuilder(psiFile)
    }
}
