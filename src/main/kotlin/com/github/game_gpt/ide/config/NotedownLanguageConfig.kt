package com.github.game_gpt.ide.config

class NotedownLanguageConfig(
    /**
     * ```note
     * <xml-self-closed/>
     * <xml-tag></xml-tag>
     * ```
     */
    val supportXmlExtension: Boolean = false,
    /**
     *
     * ````note
     * ```raw_mark
     *
     * ```
     * ````
     * */
    val supportRawExtension: Boolean = false,
)