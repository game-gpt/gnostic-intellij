package com.github.game_gpt.ide.structure

import com.github.game_gpt.ide.icons.GnosticIcons
import com.github.game_gpt.language.elements.ValkyrieClassElement
import com.github.game_gpt.language.elements.ValkyrieMicroElement
import com.github.game_gpt.language.elements.ValkyrieShaderElement
import com.github.game_gpt.language.types.ValkyrieTypes
import com.github.game_gpt.language.types.VocTypes
import com.github.game_gpt.language.types.VonTypes
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ColoredItemPresentation
import com.intellij.navigation.ItemPresentation
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.NlsSafe
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import javax.swing.Icon

class GnosticStructureViewElement(private val element: PsiElement?) : StructureViewTreeElement,
    ColoredItemPresentation {

    override fun getValue(): Any? {
        return element
    }

    override fun getPresentation(): ItemPresentation {
        return this
    }

    override fun getChildren(): Array<out TreeElement?> {
        if (element == null) return emptyArray()
        val children = mutableListOf<GnosticStructureViewElement>()
        
        // 对于根文件元素，我们需要递归查找所有子元素，而不仅仅是直接子元素
        if (element is PsiFile) {
            val allElements = PsiTreeUtil.findChildrenOfType(element, PsiElement::class.java)
            allElements.forEach {
                val icon = getIconForElement(it)
                if (icon != null) {
                    children.add(GnosticStructureViewElement(it))
                }
            }
        } else {
            // 对于非根元素，只查找直接子元素
            val childElements = PsiTreeUtil.getChildrenOfAnyType(element, PsiElement::class.java)
            childElements?.forEach {
                val icon = getIconForElement(it)
                if (icon != null) {
                    children.add(GnosticStructureViewElement(it))
                }
            }
        }
        
        return children.toTypedArray()
    }

    override fun getTextAttributesKey(): TextAttributesKey? {
        return null
    }

    override fun getPresentableText(): @NlsSafe String? {
        if (element == null) return null
        return when (element) {
            is PsiFile -> element.name
            is ValkyrieClassElement -> element.getClassName() ?: "class"
            is ValkyrieMicroElement -> element.getMicroName() ?: "micro"
            is ValkyrieShaderElement -> element.getShaderName() ?: "shader"
            else -> getPresentableTextForElement(element) ?: element.node?.elementType?.toString() ?: "Element"
        }
    }

    override fun getIcon(unused: Boolean): Icon? {
        if (element == null) return null
        return when (element) {
            is PsiFile -> GnosticIcons.FILE
            is ValkyrieClassElement -> GnosticIcons.Nodes.CLASS
            is ValkyrieMicroElement -> GnosticIcons.Nodes.MICRO
            is ValkyrieShaderElement -> GnosticIcons.Nodes.SHADER_NODE
            else -> getIconForElement(element)
        }
    }

    override fun navigate(requestFocus: Boolean) {
        if (element is Navigatable) {
            (element as Navigatable).navigate(requestFocus)
        }
    }

    override fun canNavigate(): Boolean {
        return element is Navigatable && (element as Navigatable).canNavigate()
    }

    override fun canNavigateToSource(): Boolean {
        return element is Navigatable && (element as Navigatable).canNavigateToSource()
    }

    companion object {
        fun getIconForElement(element: PsiElement): Icon? {
            val elementType = element.node?.elementType ?: return null
            return when (elementType) {
                ValkyrieTypes.KEYWORD_CLASS -> GnosticIcons.Nodes.CLASS
                ValkyrieTypes.KEYWORD_MICRO -> GnosticIcons.Nodes.MICRO
                ValkyrieTypes.KEYWORD_SHADER -> GnosticIcons.Nodes.SHADER_NODE
                ValkyrieTypes.KEYWORD_LET -> GnosticIcons.Nodes.FIELD
                ValkyrieTypes.KEYWORD_CONST -> GnosticIcons.Nodes.FIELD

                ValkyrieTypes.NAMESPACE_DECLARATION -> GnosticIcons.Nodes.NAMESPACE
                ValkyrieTypes.CLASS_DECLARATION -> GnosticIcons.Nodes.CLASS
                ValkyrieTypes.TRAIT_DECLARATION -> GnosticIcons.Nodes.TRAIT
                ValkyrieTypes.MICRO_DECLARATION -> GnosticIcons.Nodes.MICRO
                ValkyrieTypes.MEZZO_DECLARATION -> GnosticIcons.Nodes.MEZZO
                ValkyrieTypes.MACRO_DECLARATION -> GnosticIcons.Nodes.MACRO
                ValkyrieTypes.SHADER_DECLARATION -> GnosticIcons.Nodes.SHADER_NODE
                ValkyrieTypes.LET_DECLARATION -> GnosticIcons.Nodes.FIELD
                ValkyrieTypes.CONST_DECLARATION -> GnosticIcons.Nodes.FIELD
                ValkyrieTypes.FUNCTION_DECLARATION -> GnosticIcons.Nodes.MICRO
                ValkyrieTypes.ENUMS_DECLARATION -> GnosticIcons.Nodes.ENUMS
                ValkyrieTypes.ENUM_DECLARATION -> GnosticIcons.Nodes.ENUMS
                ValkyrieTypes.SCHEMA_DECLARATION -> GnosticIcons.SCHEMA
                ValkyrieTypes.MODEL_DECLARATION -> GnosticIcons.Nodes.CLASS
                ValkyrieTypes.SERVICE_DECLARATION -> GnosticIcons.Nodes.METHOD
                ValkyrieTypes.MESSAGE_DECLARATION -> GnosticIcons.Nodes.CLASS

                ValkyrieTypes.KEYWORD_NAMESPACE -> GnosticIcons.Nodes.NAMESPACE
                ValkyrieTypes.KEYWORD_CLASS -> GnosticIcons.Nodes.CLASS
                ValkyrieTypes.KEYWORD_TRAIT -> GnosticIcons.Nodes.TRAIT
                ValkyrieTypes.KEYWORD_MICRO -> GnosticIcons.Nodes.MICRO
                ValkyrieTypes.KEYWORD_MEZZO -> GnosticIcons.Nodes.MEZZO
                ValkyrieTypes.KEYWORD_MACRO -> GnosticIcons.Nodes.MACRO
                ValkyrieTypes.KEYWORD_SHADER -> GnosticIcons.Nodes.SHADER_NODE
                ValkyrieTypes.KEYWORD_LET -> GnosticIcons.Nodes.FIELD
                ValkyrieTypes.KEYWORD_CONST -> GnosticIcons.Nodes.FIELD
                ValkyrieTypes.KEYWORD_ENUMS -> GnosticIcons.Nodes.ENUMS
                ValkyrieTypes.KEYWORD_ENUM -> GnosticIcons.Nodes.ENUMS
                ValkyrieTypes.KEYWORD_SCHEMA -> GnosticIcons.SCHEMA
                ValkyrieTypes.KEYWORD_MODEL -> GnosticIcons.Nodes.CLASS
                ValkyrieTypes.KEYWORD_SERVICE -> GnosticIcons.Nodes.METHOD
                ValkyrieTypes.KEYWORD_MESSAGE -> GnosticIcons.Nodes.CLASS

                VocTypes.TEMPLATE_SECTION -> GnosticIcons.Nodes.CLASS
                VocTypes.SCRIPT_SECTION -> GnosticIcons.Nodes.MICRO
                VocTypes.STYLE_SECTION -> GnosticIcons.Nodes.CLASS

                VocTypes.STYLE_RULE -> GnosticIcons.Nodes.CLASS

                VonTypes.VON_DICT -> GnosticIcons.Nodes.CLASS
                VonTypes.VON_PAIR -> GnosticIcons.Nodes.FIELD
                VonTypes.VON_LIST -> GnosticIcons.Nodes.CLASS

                else -> null
            }
        }

        fun getPresentableTextForElement(element: PsiElement): String? {
            val node = element.node ?: return null
            val elementType = node.elementType

            when (elementType) {
                VonTypes.VON_DICT -> {
                    val typeIdentifier = node.findChildByType(VonTypes.IDENTIFIER)
                    return typeIdentifier?.text ?: "{dict}"
                }
                VonTypes.VON_PAIR -> {
                    val keyIdentifier = node.findChildByType(VonTypes.IDENTIFIER)
                    return keyIdentifier?.text ?: "pair"
                }
                VonTypes.VON_LIST -> {
                    val items = PsiTreeUtil.getChildrenOfAnyType(element, PsiElement::class.java)
                        ?.filter { it.node?.elementType == VonTypes.VON_DICT || it.node?.elementType == VonTypes.VON_LIST }
                        ?.size ?: 0
                    return if (items > 0) "[$items items]" else "[list]"
                }
            }

            val identifier = node.findChildByType(ValkyrieTypes.IDENTIFIER)
                ?: node.findChildByType(VonTypes.IDENTIFIER)
                ?: node.findChildByType(VocTypes.IDENTIFIER)
            return identifier?.text ?: element.text?.take(50)
        }
    }
}
