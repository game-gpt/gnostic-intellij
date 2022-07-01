package com.github.game_gpt.language.elements

import com.github.game_gpt.language.types.NoteTypes
import com.github.game_gpt.language.types.ValkyrieTypes
import com.github.game_gpt.language.types.VocTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

object GnosticElementFactory {
    fun createElement(node: ASTNode): PsiElement {
        val elementType = node.elementType

        return when (elementType) {
            ValkyrieTypes.CLASS_DECLARATION -> ValkyrieClassElement(node)
            ValkyrieTypes.NAMESPACE_DECLARATION -> ValkyrieNamespaceElement(node)
            ValkyrieTypes.FUNCTION_DECLARATION -> ValkyrieMicroElement(node)
            ValkyrieTypes.MICRO_DECLARATION -> ValkyrieMicroElement(node)
            ValkyrieTypes.MEZZO_DECLARATION -> ValkyrieMezzoElement(node)
            ValkyrieTypes.MACRO_DECLARATION -> ValkyrieMacroElement(node)
            ValkyrieTypes.SHADER_DECLARATION -> ValkyrieShaderElement(node)
            ValkyrieTypes.SHADER_KIND -> GnosticElement(node)
            ValkyrieTypes.SHADER_PROPERTY -> GnosticElement(node)
            ValkyrieTypes.RENDER_STATES_BLOCK -> GnosticElement(node)
            ValkyrieTypes.VERTEX_FUNCTION -> GnosticElement(node)
            ValkyrieTypes.FRAGMENT_FUNCTION -> GnosticElement(node)
            ValkyrieTypes.COMPUTE_FUNCTION -> GnosticElement(node)
            ValkyrieTypes.UNIFORMS_BLOCK -> GnosticElement(node)
            ValkyrieTypes.FALLBACK_BLOCK -> GnosticElement(node)
            ValkyrieTypes.UNIFORM_FIELD -> GnosticElement(node)
            ValkyrieTypes.SCHEMA_CONFIG_FIELD -> GnosticElement(node)
            ValkyrieTypes.MODEL_FIELD -> GnosticElement(node)
            ValkyrieTypes.SERVICE_METHOD -> GnosticElement(node)
            ValkyrieTypes.MESSAGE_FIELD -> GnosticElement(node)
            ValkyrieTypes.ENUM_VARIANT -> GnosticElement(node)
            ValkyrieTypes.ANNOTATION -> GnosticElement(node)
            ValkyrieTypes.FIELD_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.TYPE_REFERENCE -> GnosticElement(node)
            ValkyrieTypes.SCHEMA_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.MODEL_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.SERVICE_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.MESSAGE_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.ENUM_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.ENUMS_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.TRAIT_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.LET_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.CONST_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.USING_DECLARATION -> GnosticElement(node)

            VocTypes.TEMPLATE_SECTION,
            VocTypes.SCRIPT_SECTION,
            VocTypes.STYLE_SECTION,
            VocTypes.WIDGET_ELEMENT,
            VocTypes.WIDGET_TAG_NAME,
            VocTypes.WIDGET_ATTRIBUTE,
            VocTypes.WIDGET_ATTRIBUTE_NAME,
            VocTypes.WIDGET_ATTRIBUTE_VALUE,
            VocTypes.BRACE_EXPRESSION,
            VocTypes.USING_DECLARATION,
            VocTypes.LET_DECLARATION,
            VocTypes.CONST_DECLARATION,
            VocTypes.MICRO_DECLARATION,
            VocTypes.MEZZO_DECLARATION,
            VocTypes.MACRO_DECLARATION,
            VocTypes.FUNCTION_DECLARATION,
            VocTypes.BLOCK,
            VocTypes.PARAMETER_LIST,
            VocTypes.EXPRESSION,
            VocTypes.EXPRESSION_STATEMENT,
            VocTypes.IDENTIFIER_REFERENCE,
            VocTypes.STYLE_RULE,
            VocTypes.STYLE_BLOCK,
            VocTypes.STYLE_SELECTOR,
            VocTypes.STYLE_BODY,
            VocTypes.STYLE_PROPERTY,
            VocTypes.STYLE_PROPERTY_NAME,
            VocTypes.STYLE_PROPERTY_VALUE -> GnosticElement(node)

            NoteTypes.STORY_FILE,
            NoteTypes.VARIABLE_DEFINITION,
            NoteTypes.INCLUDE_STATEMENT,
            NoteTypes.SCENE_DEFINITION,
            NoteTypes.SCENE_HEADER,
            NoteTypes.SCENE_BODY,
            NoteTypes.TEXT_LINE,
            NoteTypes.DIALOGUE_LINE,
            NoteTypes.SPEAKER_NAME,
            NoteTypes.DIALOGUE_TEXT,
            NoteTypes.CHOICE_BLOCK,
            NoteTypes.CHOICE_ITEM,
            NoteTypes.CHOICE_TEXT,
            NoteTypes.CHOICE_BODY,
            NoteTypes.CONDITION_EXPRESSION,
            NoteTypes.COMMAND_CALL,
            NoteTypes.MODULE_NAME,
            NoteTypes.FUNCTION_NAME,
            NoteTypes.ARGUMENT_LIST,
            NoteTypes.VARIABLE_OPERATION,
            NoteTypes.JUMP_STATEMENT,
            NoteTypes.JUMP_TARGET,
            NoteTypes.CONDITIONAL_BLOCK,
            NoteTypes.CONDITION_BRANCH,
            NoteTypes.SEPARATOR,
            NoteTypes.EXPRESSION -> GnosticElement(node)

            else -> GnosticElement(node)
        }
    }
}
