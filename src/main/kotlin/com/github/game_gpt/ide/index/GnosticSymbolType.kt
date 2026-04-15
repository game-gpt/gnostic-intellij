package com.github.game_gpt.ide.index

/**
 * 符号类型枚举
 * 用于标识 Gnostic 语言中不同种类的符号
 */
enum class GnosticSymbolType {
    /** 命名空间声明 */
    NAMESPACE,
    /** 类声明 */
    CLASS,
    /** 函数声明 */
    FUNCTION,
    /** 变量声明 */
    VARIABLE,
    /** 常量声明 */
    CONSTANT,
    /** 枚举声明 */
    ENUM,
    /** 数据模型声明 */
    MODEL,
    /** 服务声明 */
    SERVICE,
    /** 消息声明 */
    MESSAGE,
    /** 着色器声明 */
    SHADER
}