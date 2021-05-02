/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import javafx.geometry.Pos
import javafx.scene.control.Label

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

