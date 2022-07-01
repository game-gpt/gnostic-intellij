package com.github.game_gpt.language

import com.github.game_gpt.ide.file_type.GnosticLocaleFileType
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsSafe

object GnosticLocaleLanguage : Language(GnosticObjectLanguage, "GnosticLocale") {
    override fun getDisplayName(): @NlsSafe String {
        return "Gnostic Locale"
    }

    override fun getAssociatedFileType(): LanguageFileType {
        return GnosticLocaleFileType
    }
}
