/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.libfe.fxbuilders.ButtonBuilder
import com.radixpro.enigma.libfe.fxbuilders.LabelBuilder
import com.radixpro.enigma.libfe.fxbuilders.PaneBuilder
import com.radixpro.enigma.libfe.texts.Rosetta
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.web.WebView
import javafx.stage.Modality
import javafx.stage.Stage


/**
 * Show a Help page.
 */
class Help(private val title: String, private val content: String) {
    // TODO replac with version from libFe (make sure method showContent() is public)
    private val widthInner = 560.0
    private val widthOuter = 572.0
    private val heightTitle = 40.0
    private val GAP = 6.0
    private val prefix = "<div style=\"font-family: sans-serif, Arial; font-size: 12px; background-color: white;\">"
    private val postfix = "</div>"
    private lateinit var stage: Stage
    private val styleSheet = "css/enigma.css"


    fun showContent() {
        stage = Stage()
        stage.width = widthOuter
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = Rosetta.getText("shared.helptitle")
        val buttonBar = createButtonBar()
        val titlePane = createTitlePane()
        val contentPane = createContentPane()
        BorderPane.setAlignment(titlePane, Pos.CENTER)
        BorderPane.setAlignment(contentPane, Pos.CENTER)
        BorderPane.setAlignment(buttonBar, Pos.CENTER)
        val borderPane = BorderPane()
        borderPane.prefWidth = widthOuter
        borderPane.padding = Insets(GAP, GAP, GAP, GAP)
        borderPane.stylesheets.add(styleSheet)
        borderPane.styleClass.add("helppane")
        borderPane.top = titlePane
        borderPane.center = contentPane
        borderPane.bottom = buttonBar
        stage.scene = Scene(borderPane)
        stage.show()
    }

    private fun onClose() {
        stage.close()
    }

    private fun createLblHelpTitle(): Label {
        return LabelBuilder().setText(title).setPrefWidth(widthInner).setStyleClass("titletext").build()
    }

    private fun createTitlePane(): Pane {
        return PaneBuilder().setPrefHeight(heightTitle).setPrefWidth(widthInner).setStyleClass("titlepane").setChildren(arrayListOf(createLblHelpTitle())).build()
    }

    private fun createContentPane(): Pane {
        return PaneBuilder().setPrefWidth(widthInner).setPrefHeight(400.0).setChildren(arrayListOf(createContentWebView())).build()
    }

    private fun createButtonBar(): ButtonBar {
        val buttonBar = ButtonBar()
        buttonBar.styleClass.add("helppane")
        buttonBar.buttons.add(createCloseButton())
        return buttonBar
    }

    private fun createContentWebView(): WebView {
        val webView = WebView()
        val webEngine = webView.engine
        val fullContent = prefix + content + postfix
        webView.prefWidth = widthInner - 12
        webEngine.loadContent(fullContent, "text/html")
        return webView
    }

    private fun createCloseButton(): Button {
        val button = ButtonBuilder().setText(Rosetta.getText("shared.btn_close")).setDisabled(false).build()
        button.onAction = EventHandler { onClose() }
        return button
    }

}