package com.github.game_gpt.ide.index

/**
 * 符号索引键
 * 由符号名称和所属命名空间组成，用于唯一标识一个符号
 */
data class GnosticSymbolKey(
    /** 符号名称，如 "Player"、"PlayerService" */
    val name: String,
    /** 所属命名空间，如 "game_backend"；无命名空间时为空字符串 */
    val namespace: String
)
