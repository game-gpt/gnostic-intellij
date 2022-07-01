package com.github.game_gpt.ide.structure

import com.intellij.ide.projectView.SelectableTreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.Unmodifiable

/**
 * Gnostic 项目视图结构提供者
 * 用于在项目视图中提供自定义的树结构
 */
class GnosticTreeStructureProvider : SelectableTreeStructureProvider {
    /**
     * 获取顶级元素
     * @param element Psi元素
     * @return 顶级Psi元素
     */
    override fun getTopLevelElement(element: PsiElement?): PsiElement? {
        return element
    }

    /**
     * 修改树节点
     * @param parent 父节点
     * @param children 子节点集合
     * @param settings 视图设置
     * @return 修改后的子节点集合
     */
    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>?>,
        settings: ViewSettings?
    ): @Unmodifiable Collection<AbstractTreeNode<*>?> {
        // 这里可以实现自定义的项目视图结构逻辑
        // 例如：添加自定义节点、重新组织节点结构等
        return children
    }
}
