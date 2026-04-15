package com.github.game_gpt.ide.index

import com.intellij.util.io.DataExternalizer
import java.io.DataInput
import java.io.DataOutput

/**
 * 符号信息数据外部化器
 * 负责 GnosticSymbolInfo 的序列化和反序列化
 */
class GnosticSymbolInfoExternalizer : DataExternalizer<GnosticSymbolInfo> {

    override fun save(output: DataOutput, value: GnosticSymbolInfo) {
        output.writeUTF(value.name)
        output.writeUTF(value.namespace)
        output.writeUTF(value.type.name)
        output.writeUTF(value.fileUrl)
        output.writeInt(value.offset)
        output.writeInt(value.length)
    }

    override fun read(input: DataInput): GnosticSymbolInfo {
        val name = input.readUTF()
        val namespace = input.readUTF()
        val type = GnosticSymbolType.valueOf(input.readUTF())
        val fileUrl = input.readUTF()
        val offset = input.readInt()
        val length = input.readInt()
        return GnosticSymbolInfo(name, namespace, type, fileUrl, offset, length)
    }
}
