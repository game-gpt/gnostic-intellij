package com.github.game_gpt.ide.file

import com.github.game_gpt.ide.file_type.GnosticShaderFileType
import com.github.game_gpt.language.GnosticShaderLanguage
import com.github.game_gpt.language.elements.ValkyrieNamespaceElement
import com.github.game_gpt.language.elements.ValkyrieUsingElement
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

/**
 * Gnostic Shader 文件 PSI 元素
 * 表示一个 .shader 文件，提供文件级命名空间和 using 声明查询
 */
class GnosticShaderFile(view: FileViewProvider) : GnosticFile(view, GnosticShaderLanguage) {

    override fun getFileType(): FileType {
        return GnosticShaderFileType
    }

    /**
     * 获取文件中第一个命名空间声明的完整路径
     * 对于 `namespace my_shaders::common;`，返回 "my_shaders::common"
     * 如果文件没有命名空间声明，返回 null
     */
    fun getNamespace(): String? {
        for (child in children) {
            if (child is ValkyrieNamespaceElement) {
                return child.getNamespacePath()
            }
        }
        return null
    }

    /**
     * 获取文件中所有的 using 声明元素
     * 返回按文件中声明顺序排列的 ValkyrieUsingElement 列表
     */
    fun getUsingDeclarations(): List<ValkyrieUsingElement> {
        val result = mutableListOf<ValkyrieUsingElement>()
        for (child in children) {
            if (child is ValkyrieUsingElement) {
                result.add(child)
            }
        }
        return result
    }

    /**
     * 获取文件中所有 using 声明的导入路径
     * 等价于 getUsingDeclarations().mapNotNull { it.getImportPath() }
     */
    fun getUsingPaths(): List<String> {
        return getUsingDeclarations().mapNotNull { it.getImportPath() }
    }
}
