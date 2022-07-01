package com.github.game_gpt.ide.structure

import com.intellij.ide.structureView.StructureViewClickEvent
import com.intellij.ide.structureView.StructureViewModel.*
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Filter
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import java.util.concurrent.CompletableFuture

class GnosticStructureViewModel : StructureViewModelBase, ElementInfoProvider, ExpandInfoProvider, ClickHandler {
    constructor(psi: PsiFile, editor: Editor?) : super(psi, editor, GnosticStructureViewElement(psi))

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun getFilters(): Array<Filter> {
        return emptyArray()
    }

    override fun isAutoExpand(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun isSmartExpand(): Boolean {
        return false
    }

    override fun handleClick(event: StructureViewClickEvent): CompletableFuture<Boolean?> {
        TODO("Not yet implemented")
    }
}
