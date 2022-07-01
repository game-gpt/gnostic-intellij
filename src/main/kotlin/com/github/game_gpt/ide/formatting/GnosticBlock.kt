package com.github.game_gpt.ide.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import org.jetbrains.annotations.Unmodifiable

/**
 * Gnostic 代码格式化块
 * 用于表示格式化过程中的代码块结构
 *
 * @property node AST 节点，包含此块的语法树信息
 */
class GnosticBlock(private val node: ASTNode) : Block {

    private val _subBlocks: List<Block> by lazy {
        node.getChildren(null).map { GnosticBlock(it) }
    }

    override fun getTextRange(): TextRange {
        return node.textRange
    }

    override fun getSubBlocks(): @Unmodifiable List<Block> {
        return _subBlocks
    }

    override fun getWrap(): Wrap? {
        return null
    }

    override fun getIndent(): Indent? {
        return null
    }

    override fun getAlignment(): Alignment? {
        return null
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return null
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        return ChildAttributes(null, null)
    }

    override fun isIncomplete(): Boolean {
        return false
    }

    override fun isLeaf(): Boolean {
        return node.firstChildNode == null
    }
}