package com.github.game_gpt.ide.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

/**
 * Gnostic 代码检查工具
 * 用于检查代码中的错误和潜在问题
 */
class GnosticInspection : LocalInspectionTool() {

    /**
     * 获取检查工具的显示名称
     * @return 显示名称
     */
    override fun getDisplayName(): String {
        return "Gnostic Code Inspection"
    }

    /**
     * 获取检查工具的分组名称
     * @return 分组名称
     */
    override fun getGroupDisplayName(): String {
        return "Gnostic"
    }

    /**
     * 获取检查工具的短名称
     * @return 短名称
     */
    override fun getShortName(): String {
        return "GnosticInspection"
    }

    /**
     * 创建访问者来检查代码
     * @param holder 问题持有者
     * @param isOnTheFly 是否在实时模式下
     * @return Psi元素访问者
     */
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            // 这里可以重写各种visit方法来检查不同类型的Psi元素
            // 例如：visitClass, visitMethod, visitVariable等
        }
    }

}
