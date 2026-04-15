package com.github.game_gpt.ide.index

/**
 * 符号索引值
 * 存储符号的详细信息，包括名称、命名空间、类型和位置
 */
data class GnosticSymbolInfo(
    /** 符号名称 */
    val name: String,
    /** 所属命名空间 */
    val namespace: String,
    /** 符号类型 */
    val type: GnosticSymbolType,
    /** 所在文件的 URL 路径 */
    val fileUrl: String,
    /** 符号在文件中的起始偏移量 */
    val offset: Int,
    /** 符号文本的长度 */
    val length: Int
)
