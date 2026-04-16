package com.github.game_gpt.ide.index

import com.intellij.util.io.DataExternalizer
import java.io.DataInput
import java.io.DataOutput

/**
 * GnosticSymbolInfo 数据外部化器
 * 用于将 GnosticSymbolInfo 序列化和反序列化到索引存储
 */
class GnosticSymbolInfoExternalizer : DataExternalizer<GnosticSymbolInfo> {

    /**
     * 将 GnosticSymbolInfo 序列化到输出流
     */
    override fun save(output: DataOutput, value: GnosticSymbolInfo) {
        output.writeUTF(value.name)
        output.writeUTF(value.namespace)
        output.writeUTF(value.type.name)
        output.writeUTF(value.fileUrl)
        output.writeInt(value.offset)
        output.writeInt(value.length)
    }

    /**
     * 从输入流反序列化 GnosticSymbolInfo
     */
    override fun read(input: DataInput): GnosticSymbolInfo {
        val name = input.readUTF()
        val namespace = input.readUTF()
        val typeName = input.readUTF()
        val fileUrl = input.readUTF()
        val offset = input.readInt()
        val length = input.readInt()
        return GnosticSymbolInfo(
            name = name,
            namespace = namespace,
            type = GnosticSymbolType.valueOf(typeName),
            fileUrl = fileUrl,
            offset = offset,
            length = length
        )
    }
}
