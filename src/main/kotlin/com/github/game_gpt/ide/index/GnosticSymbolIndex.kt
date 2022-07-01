package com.github.game_gpt.ide.index

import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.KeyDescriptor

// 符号索引器
class GnosticSymbolIndex : FileBasedIndexExtension<GnosticSymbolKey, GnosticSymbolInfo>() {
    override fun getName(): ID<GnosticSymbolKey?, GnosticSymbolInfo?> {
        TODO("Not yet implemented")
    }

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        TODO("Not yet implemented")
    }

    override fun dependsOnFileContent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getIndexer(): DataIndexer<GnosticSymbolKey?, GnosticSymbolInfo?, FileContent?> {
        TODO("Not yet implemented")
    }

    override fun getKeyDescriptor(): KeyDescriptor<GnosticSymbolKey?> {
        TODO("Not yet implemented")
    }

    override fun getValueExternalizer(): DataExternalizer<GnosticSymbolInfo?> {
        TODO("Not yet implemented")
    }

    override fun getVersion(): Int {
        TODO("Not yet implemented")
    }

}
