/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXCheckBox
import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXTextField
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ButtonBar
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.paint.Color


/**
 * Builder for JFXCheckBox. <br/>
 * Default: focusTraversable: true, selected: false, disabled: false.<br/>
 * CheckedColor is always SteelBlue.<br/>
 */
class CheckBoxBuilder {

    private val colorChecked = Color.STEELBLUE
    private var align: Pos? = null
    private var styleClass = ""
    private var text = ""
    private var selected = false
    private var focusTraversable = true

    fun setAlignment(align: Pos): CheckBoxBuilder {
        this.align = align
        return this
    }

    fun setStyleClass(styleClass: String): CheckBoxBuilder {
        this.styleClass = styleClass
        return this
    }

    fun setSelected(selected: Boolean): CheckBoxBuilder {
        this.selected = selected
        return this
    }

    fun setFocusTraversable(focusTrav: Boolean): CheckBoxBuilder {
        this.focusTraversable = focusTrav
        return this
    }

    fun setText(text: String): CheckBoxBuilder {
        this.text = text
        return this
    }

    fun build(): JFXCheckBox {
        val checkBox = JFXCheckBox()
        checkBox.text = text
        checkBox.checkedColor = colorChecked
        checkBox.isFocusTraversable = focusTraversable
        checkBox.isSelected = selected
        if (null != align) checkBox.alignment = align
        if (styleClass.isNotBlank()) checkBox.styleClass.add(styleClass)
        return checkBox
    }
}




/**
 * Creates a label for info text.Text is always wrappable for a given value of maxWidth.
 */
class InfoLabelBuilder {
    private var text = ""
    private var maxWidth = 0.0
    private var prefWidth = 0.0
    private var prefHeight = 0.0
    private var alignment: Pos? = null
    private var styleClass: String = ""
    private var visible = true

    fun setText(text: String): InfoLabelBuilder {
        this.text = text
        return this
    }

    fun setVisible(visible: Boolean): InfoLabelBuilder {
        this.visible = visible
        return this
    }

    fun setMaxWidth(maxWidth: Double): InfoLabelBuilder {
        this.maxWidth = maxWidth
        return this
    }

    fun setPrefWidth(prefWidth: Double): InfoLabelBuilder {
        this.prefWidth = prefWidth
        return this
    }

    fun setPrefHeight(prefHeight: Double): InfoLabelBuilder {
        this.prefHeight = prefHeight
        return this
    }

    fun setStyleClass(styleClass: String): InfoLabelBuilder {
        this.styleClass = styleClass
        return this
    }

    fun setAlignment(alignment: Pos): InfoLabelBuilder {
        this.alignment = alignment
        return this
    }

    fun build(): Label {
        val lblText: String = text
        val label = Label(lblText)
        label.isWrapText = true
        label.isVisible = visible
        if (prefWidth > 0.0) label.prefWidth = prefWidth
        if (maxWidth > 0.0) label.maxWidth = maxWidth
        if (prefHeight > 0.0) label.prefHeight = prefHeight
        if (styleClass.isNotEmpty()) label.styleClass.add(styleClass)
        if (null != alignment) label.alignment = alignment
        return label
    }
}


/**
 * Builder for JFXButton.
 * Defaults: <ul><li>text: ""</li><li>disabled: false</li><li>focusTraversable: false</li></ul>
 */
class ButtonBuilder {
    private var text = ""
    private var disabled = false
    private var focusTraversable = false
    private var alignment: Pos? = null
    private var prefHeight = 0.0
    private var prefWidth = 0.0
    private var padding = Insets(0.0)

    fun setText(text: String): ButtonBuilder {
        this.text = text
        return this
    }

    fun setPrefHeight(height: Double): ButtonBuilder {
        this.prefHeight = height
        return this
    }

    fun setPrefWidth(width: Double): ButtonBuilder {
        this.prefWidth = width
        return this
    }

    fun setAlignment(alignment: Pos): ButtonBuilder {
        this.alignment = alignment
        return this
    }

    fun setPadding(padding: Insets): ButtonBuilder {
        this.padding = padding
        return this
    }

    fun setDisabled(disabled: Boolean): ButtonBuilder {
        this.disabled = disabled
        return this
    }

    fun setFocusTraversable(focusTraversable: Boolean): ButtonBuilder {
        this.focusTraversable = focusTraversable
        return this
    }

    fun build(): JFXButton {
        val button = JFXButton(text)
        if (padding != Insets(0.0)) button.padding = padding
        if (null != alignment) button.alignment = alignment
        button.isDisable = disabled
        button.isFocusTraversable = focusTraversable
        if (prefHeight > 0.0) button.prefHeight = prefHeight
        if (prefWidth > 0.0) button.prefWidth = prefWidth
        return button
    }

}


/**
 * Builder for Buttonbar. No specific defaults.
 */
class ButtonBarBuilder {

    private var prefWidth = 0.0
    private var prefHeight = 0.0
    private var buttons = arrayListOf<JFXButton>()

    fun setPrefHeight(prefHeight: Double): ButtonBarBuilder {
        this.prefHeight = prefHeight
        return this
    }

    fun setPrefWidth(prefWidth: Double): ButtonBarBuilder {
        this.prefWidth = prefWidth
        return this
    }

    fun setButtons(vararg buttons: JFXButton): ButtonBarBuilder {
        this.buttons.addAll(buttons)
        return this
    }

    fun build(): ButtonBar {
        val buttonBar = ButtonBar()
        if (prefHeight > 0.0) buttonBar.prefHeight = prefHeight
        if (prefWidth > 0.0) buttonBar.prefWidth = prefWidth
        if (buttons.isNotEmpty()) buttonBar.buttons.addAll(buttons)
        return buttonBar
    }
}

/**
 * Builder for JFXComboBox. Default value for disabled: false.
 */
class ComboBoxBuilder {

    private var prefHeight = 0.0
    private var prefWidth = 0.0
    private var styleClass: String = ""
    private var items= mutableListOf<String>()
    private var disabled = false

    fun setPrefHeight(prefHeight: Double): ComboBoxBuilder {
        this.prefHeight = prefHeight
        return this
    }

    fun setPrefWidth(prefWidth: Double): ComboBoxBuilder {
        this.prefWidth = prefWidth
        return this
    }

    fun setStyleClass(styleClass: String): ComboBoxBuilder {
        this.styleClass = styleClass
        return this
    }

    fun setItems(vararg items: String): ComboBoxBuilder {
        this.items.addAll(items)
        return this
    }

    fun setItems(items: List<String>): ComboBoxBuilder {
        this.items.addAll(items)
        return this
    }

    fun setDisabled(disabled: Boolean): ComboBoxBuilder {
        this.disabled = disabled
        return this
    }

    fun build(): JFXComboBox<String> {
        val comboBox = JFXComboBox<String>()
        comboBox.isDisable = disabled
        if (prefWidth > 0.0) comboBox.prefWidth = prefWidth
        if (prefHeight > 0.0) comboBox.prefHeight = prefHeight
        if (styleClass.isNotEmpty()) comboBox.styleClass.add(styleClass)
        comboBox.items.clear()
        comboBox.items.addAll(items)
        return comboBox
    }
}



/**
 * Builder for JFTextField. No defaults.
 */
class TextFieldBuilder {

    private var prefHeight = 0.0
    private var prefWidth = 0.0
    private var maxWidth = 0.0
    private var styleClass = ""
    private var text = ""
    private var promptText = ""
    private var alignment: Pos? = null

    fun setPrefHeight(prefHeight: Double): TextFieldBuilder {
        this.prefHeight = prefHeight
        return this
    }

    fun setPrefWidth(prefWidth: Double): TextFieldBuilder {
        this.prefWidth = prefWidth
        return this
    }

    fun setMaxWidth(maxWidth: Double): TextFieldBuilder {
        this.maxWidth = maxWidth
        return this
    }

    fun setStyleClass(styleClass: String): TextFieldBuilder {
        this.styleClass = styleClass
        return this
    }

    fun setText(text: String): TextFieldBuilder {
        this.text = text
        return this
    }

    fun setPromptText(text: String): TextFieldBuilder {
        this.promptText = text
        return this
    }

    fun setAlignment(alignment: Pos): TextFieldBuilder {
        this.alignment = alignment
        return this
    }

    fun build(): JFXTextField {
        val textField = JFXTextField()
        if (prefHeight > 0.0) textField.prefHeight = prefHeight
        if (prefWidth > 0.0) textField.prefWidth = prefWidth
        if (maxWidth > 0.0) textField.maxWidth = maxWidth
        if (styleClass.isNotEmpty()) textField.styleClass.add(styleClass)
        if (text.isNotEmpty()) textField.text = text
        if (promptText.isNotEmpty()) textField.promptText = promptText
        if (null != alignment) textField.alignment = alignment
        return textField
    }

}