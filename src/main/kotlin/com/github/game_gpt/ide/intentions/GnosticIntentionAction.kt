package com.github.game_gpt.ide.intentions

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException

/**
 * Gnostic 代码意图操作
 * 用于提供代码改进建议
 */
class GnosticIntentionAction : PsiElementBaseIntentionAction(), IntentionAction {
    /**
     * 获取实现类名称
     * @return 实现类名称
     */
    fun getImplementationClassName(): String {
        return this.javaClass.name
    }

    /**
     * 获取操作名称
     * @return 操作名称
     */
    override fun getText(): String {
        return "Gnostic: Add Documentation"
    }

    /**
     * 获取族名称
     * @return 族名称
     */
    override fun getFamilyName(): String {
        return "Gnostic"
    }

    /**
     * 检查是否适用于指定元素
     * @param project 项目
     * @param editor 编辑器
     * @param element 元素
     * @return 是否适用
     */
    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        return true
    }

    /**
     * 执行操作
     * @param project 项目
     * @param editor 编辑器
     * @param element 元素
     * @throws IncorrectOperationException 操作异常
     */
    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        // 这里实现代码意图操作逻辑
        // 例如：添加文档注释、修复语法错误等
    }

    /**
     * 检查是否需要写入操作
     * @return 是否需要写入操作
     */
    override fun startInWriteAction(): Boolean {
        return true
    }

}
