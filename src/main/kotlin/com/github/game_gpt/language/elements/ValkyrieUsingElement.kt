package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.ValkyrieTypes
import com.intellij.lang.ASTNode

/**
 * Using 声明 PSI 元素
 * 表示 gs 语言中的 using 声明，如 `using gg_shader::f32::{vec2, vec3};`
 */
class ValkyrieUsingElement(node: ASTNode) : ValkyrieElement(node) {

    /**
     * 获取完整的导入路径（使用双冒号分隔符，符合 shader 语言规范）
     * 对于 `using gg_shader::f32::vec2`，返回 "gg_shader::f32::vec2"
     * 对于 `using my_shaders::common::*`，返回 "my_shaders::common::*"
     */
    fun getImportPath(): String? {
        val parts = mutableListOf<String>()
        var child = node.firstChildNode
        while (child != null) {
            if (child.elementType == ValkyrieTypes.IDENTIFIER) {
                parts.add(child.text)
            } else if (child.elementType == ValkyrieTypes.MULTIPLY) {
                parts.add("*")
            }
            child = child.treeNext
        }
        return parts.takeIf { it.isNotEmpty() }?.joinToString("::")
    }

    /**
     * 获取导入的命名空间部分
     * 对于 `using gg_shader::f32::vec2`，返回 "gg_shader::f32"
     * 对于 `using my_shaders::common::*`，返回 "my_shaders::common"
     */
    fun getImportNamespace(): String? {
        val path = getImportPath() ?: return null
        val lastSep = path.lastIndexOf("::")
        return if (lastSep > 0) path.substring(0, lastSep) else ""
    }

    /**
     * 获取导入的符号名称
     * 对于 `using gg_shader::f32::vec2`，返回 "vec2"
     * 对于 `using my_shaders::common::*`，返回 "*"（表示通配符导入）
     */
    fun getImportedName(): String? {
        val path = getImportPath() ?: return null
        val lastSep = path.lastIndexOf("::")
        return if (lastSep >= 0) path.substring(lastSep + 2) else path
    }

    /**
     * 判断是否为通配符导入
     */
    fun isWildcardImport(): Boolean {
        return getImportedName() == "*"
    }

    override fun toString(): String = "Using ${getImportPath() ?: "<empty>"}"
}
