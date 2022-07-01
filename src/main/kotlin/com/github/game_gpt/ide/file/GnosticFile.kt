package com.github.game_gpt.ide.file

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.psi.FileViewProvider

abstract class GnosticFile(view: FileViewProvider, language: Language) : PsiFileBase(view, language)
