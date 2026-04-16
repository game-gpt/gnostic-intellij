package com.github.game_gpt.language.elements

import com.github.game_gpt.language.GnosticScriptLanguage
import com.github.game_gpt.language.types.NoteTypes
import com.github.game_gpt.language.types.ValkyrieTypes
import com.github.game_gpt.language.types.VocTypes
import com.github.game_gpt.language.types.VonTypes
import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory

/**
 * Gnostic PSI 元素工厂
 * 负责根据 AST 节点类型创建对应的 PSI 元素实例
 */
object GnosticElementFactory {

    /**
     * 根据 AST 节点类型创建对应的 PSI 元素
     */
    fun createElement(node: ASTNode): PsiElement {
        val elementType = node.elementType

        return when (elementType) {
            // ==================== Valkyrie 语言 ====================
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
            ValkyrieTypes.MODEL_DECLARATION -> ValkyrieModelElement(node)
            ValkyrieTypes.SERVICE_DECLARATION -> ValkyrieServiceElement(node)
            ValkyrieTypes.MESSAGE_DECLARATION -> ValkyrieMessageElement(node)
            ValkyrieTypes.ENUM_DECLARATION -> ValkyrieEnumsElement(node)
            ValkyrieTypes.ENUMS_DECLARATION -> ValkyrieEnumsElement(node)
            ValkyrieTypes.TRAIT_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.LET_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.CONST_DECLARATION -> GnosticElement(node)
            ValkyrieTypes.USING_DECLARATION -> ValkyrieUsingElement(node)

            // ==================== VON 语言 ====================
            VonTypes.VON_DICT -> VonDictElement(node)
            VonTypes.VON_LIST -> VonListElement(node)
            VonTypes.VON_PAIR -> VonPairElement(node)

            // ==================== Notedown 语言 ====================
            NoteTypes.STORY_FILE -> NoteStoryElement(node)
            NoteTypes.VARIABLE_DEFINITION -> NoteVariableElement(node)
            NoteTypes.INCLUDE_STATEMENT -> NoteIncludeElement(node)
            NoteTypes.SCENE_DEFINITION -> NoteSceneElement(node)
            NoteTypes.SCENE_HEADER -> GnosticElement(node)
            NoteTypes.SCENE_BODY -> NoteSceneBodyElement(node)
            NoteTypes.TEXT_LINE -> NoteTextLineElement(node)
            NoteTypes.DIALOGUE_LINE -> NoteDialogueElement(node)
            NoteTypes.SPEAKER_NAME -> GnosticElement(node)
            NoteTypes.DIALOGUE_TEXT -> GnosticElement(node)
            NoteTypes.CHOICE_BLOCK -> NoteChoiceBlockElement(node)
            NoteTypes.CHOICE_ITEM -> NoteChoiceItemElement(node)
            NoteTypes.CHOICE_TEXT -> GnosticElement(node)
            NoteTypes.CHOICE_BODY -> NoteChoiceBodyElement(node)
            NoteTypes.CONDITION_EXPRESSION -> GnosticElement(node)
            NoteTypes.COMMAND_CALL -> NoteCommandElement(node)
            NoteTypes.MODULE_NAME -> GnosticElement(node)
            NoteTypes.FUNCTION_NAME -> GnosticElement(node)
            NoteTypes.ARGUMENT_LIST -> GnosticElement(node)
            NoteTypes.VARIABLE_OPERATION -> NoteVariableOpElement(node)
            NoteTypes.JUMP_STATEMENT -> NoteJumpElement(node)
            NoteTypes.JUMP_TARGET -> GnosticElement(node)
            NoteTypes.CONDITIONAL_BLOCK -> NoteConditionalElement(node)
            NoteTypes.CONDITION_BRANCH -> NoteConditionBranchElement(node)
            NoteTypes.SEPARATOR -> GnosticElement(node)
            NoteTypes.EXPRESSION -> NoteExpressionElement(node)

            // ==================== VOC 语言 ====================
            // 区块
            VocTypes.TEMPLATE_SECTION -> VocTemplateSectionElement(node)
            VocTypes.SCRIPT_SECTION -> VocScriptSectionElement(node)
            VocTypes.STYLE_SECTION -> VocStyleSectionElement(node)

            // 模板元素
            VocTypes.WIDGET_ELEMENT -> VocWidgetElement(node)
            VocTypes.WIDGET_TAG_NAME -> GnosticElement(node)
            VocTypes.WIDGET_ATTRIBUTE -> VocWidgetAttributeElement(node)
            VocTypes.WIDGET_ATTRIBUTE_NAME -> GnosticElement(node)
            VocTypes.WIDGET_ATTRIBUTE_VALUE -> GnosticElement(node)
            VocTypes.BRACE_EXPRESSION -> VocBraceExpressionElement(node)

            // 脚本元素
            VocTypes.USING_DECLARATION -> VocUsingElement(node)
            VocTypes.LET_DECLARATION -> VocVariableElement(node)
            VocTypes.CONST_DECLARATION -> VocVariableElement(node)
            VocTypes.MICRO_DECLARATION -> VocFunctionElement(node)
            VocTypes.MEZZO_DECLARATION -> VocFunctionElement(node)
            VocTypes.MACRO_DECLARATION -> VocFunctionElement(node)
            VocTypes.FUNCTION_DECLARATION -> VocFunctionElement(node)
            VocTypes.BLOCK -> VocBlockElement(node)
            VocTypes.PARAMETER_LIST -> VocParameterListElement(node)
            VocTypes.EXPRESSION -> VocExpressionElement(node)
            VocTypes.EXPRESSION_STATEMENT -> GnosticElement(node)
            VocTypes.IDENTIFIER_REFERENCE -> VocIdentifierRefElement(node)

            // 样式元素
            VocTypes.STYLE_RULE -> VocStyleRuleElement(node)
            VocTypes.STYLE_BLOCK -> GnosticElement(node)
            VocTypes.STYLE_SELECTOR -> GnosticElement(node)
            VocTypes.STYLE_BODY -> GnosticElement(node)
            VocTypes.STYLE_PROPERTY -> VocStylePropertyElement(node)
            VocTypes.STYLE_PROPERTY_NAME -> GnosticElement(node)
            VocTypes.STYLE_PROPERTY_VALUE -> GnosticElement(node)

            // ==================== 默认 ====================
            else -> GnosticElement(node)
        }
    }

    /**
     * 创建标识符 PSI 元素
     * 用于重命名操作中创建新的标识符节点
     */
    fun createIdentifier(name: String, project: Project): PsiElement? {
        val file = PsiFileFactory.getInstance(project)
            .createFileFromText("__dummy__.script", GnosticScriptLanguage, "namespace $name;")
        val namespace = file.firstChild ?: return null
        var child = namespace.firstChild
        while (child != null) {
            if (child.node.elementType == ValkyrieTypes.IDENTIFIER) {
                return child
            }
            child = child.nextSibling
        }
        return null
    }
}
