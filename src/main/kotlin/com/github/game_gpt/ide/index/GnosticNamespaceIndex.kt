package com.github.game_gpt.ide.index

import com.github.game_gpt.ide.file_type.GnosticShaderFileType
import com.github.game_gpt.language.elements.ValkyrieNamespaceElement
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileBasedIndex
import com.intellij.util.indexing.FileBasedIndexExtension
import com.intellij.util.indexing.FileContent
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor

/**
 * Gnostic 命名空间索引
 * 索引项目中所有 .shader 文件内的命名空间声明，
 * 支持通过命名空间路径进行跨文件查找，使 using 声明可以解析到对应的 namespace
 */
class GnosticNamespaceIndex : FileBasedIndexExtension<String, GnosticSymbolInfo>() {

    companion object {
        /** 索引唯一标识 */
        val NAME: com.intellij.util.indexing.ID<String, GnosticSymbolInfo> =
            com.intellij.util.indexing.ID.create("GnosticNamespaceIndex")
    }

    /** 索引版本号 */
    override fun getVersion(): Int = 1

    /** 依赖文件内容 */
    override fun dependsOnFileContent(): Boolean = true

    /** 获取索引名称 */
    override fun getName(): com.intellij.util.indexing.ID<String, GnosticSymbolInfo> = NAME

    /** 获取键描述符 */
    override fun getKeyDescriptor(): KeyDescriptor<String> = EnumeratorStringDescriptor.INSTANCE

    /** 获取值外部化器 */
    override fun getValueExternalizer(): GnosticSymbolInfoExternalizer = GnosticSymbolInfoExternalizer()

    /** 获取输入过滤器，仅索引 .shader 文件 */
    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter { file: VirtualFile ->
            file.fileType is GnosticShaderFileType
        }
    }

    /** 获取索引器 */
    override fun getIndexer(): DataIndexer<String, GnosticSymbolInfo, FileContent> {
        return DataIndexer { inputData: FileContent ->
            val result = mutableMapOf<String, GnosticSymbolInfo>()
            val psiFile = inputData.psiFile
            val filePath = inputData.file.path

            for (child in psiFile.children) {
                if (child is ValkyrieNamespaceElement) {
                    val namespacePath = child.getNamespacePath()
                    if (namespacePath != null) {
                        result[namespacePath] = GnosticSymbolInfo(
                            name = namespacePath,
                            namespace = namespacePath,
                            type = GnosticSymbolType.NAMESPACE,
                            file = filePath,
                            offset = child.textOffset,
                            length = child.textLength
                        )
                    }
                }
            }

            result
        }
    }
}
