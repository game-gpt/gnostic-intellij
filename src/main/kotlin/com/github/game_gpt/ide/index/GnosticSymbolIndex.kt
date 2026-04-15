package com.github.game_gpt.ide.index

import com.github.game_gpt.ide.file_type.GnosticShaderFileType
import com.github.game_gpt.language.elements.ValkyrieNamespaceElement
import com.github.game_gpt.language.elements.ValkyrieShaderElement
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.FileContent
import com.intellij.util.io.KeyDescriptor

/**
 * Gnostic 符号索引
 * 索引项目中所有 .shader 文件内的符号声明（shader、namespace 等），
 * 支持通过符号名称和命名空间进行跨文件查找
 */
class GnosticSymbolIndex : FileBasedIndexExtension<GnosticSymbolKey, GnosticSymbolInfo>() {

    companion object {
        /** 索引唯一标识 */
        val NAME: com.intellij.util.indexing.ID<GnosticSymbolKey, GnosticSymbolInfo> =
            com.intellij.util.indexing.ID.create("GnosticSymbolIndex")
    }

    /** 索引版本号 */
    override fun getVersion(): Int = 2

    /** 依赖文件内容 */
    override fun dependsOnFileContent(): Boolean = true

    /** 获取索引名称 */
    override fun getName(): com.intellij.util.indexing.ID<GnosticSymbolKey, GnosticSymbolInfo> = NAME

    /** 获取键描述符 */
    override fun getKeyDescriptor(): KeyDescriptor<GnosticSymbolKey> = GnosticSymbolKeyDescriptor()

    /** 获取值外部化器 */
    override fun getValueExternalizer(): GnosticSymbolInfoExternalizer = GnosticSymbolInfoExternalizer()

    /** 获取输入过滤器，仅索引 .shader 文件 */
    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter { file: VirtualFile ->
            file.fileType is GnosticShaderFileType
        }
    }

    /** 获取索引器 */
    override fun getIndexer(): DataIndexer<GnosticSymbolKey, GnosticSymbolInfo, FileContent> {
        return DataIndexer { inputData: FileContent ->
            val result = mutableMapOf<GnosticSymbolKey, GnosticSymbolInfo>()
            val psiFile = inputData.psiFile
            val fileUrl = inputData.file.url

            val namespace = findFileNamespace(psiFile)

            for (child in psiFile.children) {
                if (child is ValkyrieShaderElement) {
                    val shaderName = child.getShaderName()
                    if (shaderName != null) {
                        val key = GnosticSymbolKey(shaderName, namespace)
                        result[key] = GnosticSymbolInfo(
                            name = shaderName,
                            namespace = namespace,
                            type = GnosticSymbolType.SHADER,
                            fileUrl = fileUrl,
                            offset = child.textOffset,
                            length = child.textLength
                        )
                    }
                }
            }

            result
        }
    }

    /**
     * 从 PSI 文件中提取第一个命名空间声明的路径
     */
    private fun findFileNamespace(psiFile: com.intellij.psi.PsiFile): String {
        for (child in psiFile.children) {
            if (child is ValkyrieNamespaceElement) {
                return child.getName() ?: ""
            }
        }
        return ""
    }
}
