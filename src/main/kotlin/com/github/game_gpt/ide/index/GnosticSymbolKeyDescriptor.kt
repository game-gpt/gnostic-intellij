package com.github.game_gpt.ide.index

import com.intellij.util.io.KeyDescriptor
import java.io.DataInput
import java.io.DataOutput

/**
 * 符号键描述符
 * 负责 GnosticSymbolKey 的序列化、反序列化和比较
 */
class GnosticSymbolKeyDescriptor : KeyDescriptor<GnosticSymbolKey> {

    override fun save(output: DataOutput, value: GnosticSymbolKey) {
        output.writeUTF(value.name)
        output.writeUTF(value.namespace)
    }

    override fun read(input: DataInput): GnosticSymbolKey {
        val name = input.readUTF()
        val namespace = input.readUTF()
        return GnosticSymbolKey(name, namespace)
    }

    override fun getHashCode(value: GnosticSymbolKey): Int {
        return value.name.hashCode() * 31 + value.namespace.hashCode()
    }

    override fun isEqual(val1: GnosticSymbolKey, val2: GnosticSymbolKey): Boolean {
        return val1.name == val2.name && val1.namespace == val2.namespace
    }
}
