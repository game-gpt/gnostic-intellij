package com.github.game_gpt.ide.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

class GnosticStructureViewBuilder(private val psiFile: PsiFile) : TreeBasedStructureViewBuilder() {
    override fun createStructureViewModel(editor: Editor?): StructureViewModel {
        return GnosticStructureViewModel(psiFile, editor)
    }
}
