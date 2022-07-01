package com.github.game_gpt.language.visitor

import com.intellij.psi.PsiRecursiveVisitor

/**
 * Gnostic 递归访问器
 * 用于递归访问各种 Gnostic 格式的文件元素
 */
open class GnosticRecursiveVisitor : GnosticVisitor(), PsiRecursiveVisitor {

}
