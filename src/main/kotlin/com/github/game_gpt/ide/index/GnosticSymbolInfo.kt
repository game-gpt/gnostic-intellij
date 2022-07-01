package com.github.game_gpt.ide.index

// 符号信息
data class GnosticSymbolInfo(
    val name: String,
    val namespace: String,
    val type: GnosticSymbolType,
    val file: String,
    val offset: Int,
    val length: Int
)