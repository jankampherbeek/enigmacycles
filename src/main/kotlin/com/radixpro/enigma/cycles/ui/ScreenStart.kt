/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.cycles.helpers.InfoLabelBuilder
import com.radixpro.enigma.cycles.helpers.LanguageManager
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_TITLE_PANE
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_TITLE_TEXT
import com.radixpro.enigma.cycles.ui.UiDictionary.TITLE_HEIGHT
import com.radixpro.enigma.libfe.fxbuilders.*
import com.radixpro.enigma.libfe.texts.Rosetta
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.application.Platform
import javafx.event.EventHandler

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*

class ScreenStart(private val screenInput: ScreenInput,
                  private val screenAbout: ScreenAbout,
                  private val screenManual: ScreenManual,
                  private val languageManager: LanguageManager) {

    private val height = 440.0
    private val width = 660.0
    private lateinit var language: String
    private lateinit var version: String
    private lateinit var btnStart: Button
    private lateinit var btnExit: Button
    private lateinit var buttonBar: ButtonBar
    private lateinit var menuBar: MenuBar
    private lateinit var miLanguageDu: MenuItem
    private lateinit var miLanguageEn: MenuItem
    private lateinit var miUserManual: MenuItem
    private lateinit var miAbout: MenuItem
    private lateinit var miCycles: MenuItem

    private var stage = Stage()

fun show() {
    stage.initModality(Modality.WINDOW_MODAL)
    initialize()
}

    private fun initialize() {
        defineVersion()
        defineButtonBar()
        defineMenuBar()
        defineLanguage()
        defineListeners()
        stage.title = getText("screenstart.title") + " " + version
        stage.scene = Scene(defineGridPane())
        stage.show()

    }

    private fun defineGridPane(): GridPane {
        val grid = GridPaneBuilder()
            .setHGap(UiDictionary.GAP)
            .setPrefWidth(width)
            .setPrefHeight(height)
            .setPadding(Insets(UiDictionary.GAP))
            .setStyleSheet(UiDictionary.STYLESHEET)
            .build()
        grid.add(menuBar, 0, 0, 2, 1)
        grid.add(createTitlePane(), 0, 1, 2, 1)
        grid.add(createImage(), 0, 2, 1, 2)
        grid.add(createTextPane(), 1, 2, 1, 1)
        grid.add(buttonBar, 1, 3, 1, 1)
        return grid
    }

    private fun createTextPane(): Pane {
        val label =  InfoLabelBuilder().setText(getText("screenstart.lblexplanation")).setPrefWidth(300.0)
            .setMaxWidth(280.0).build()
        label.alignment = Pos.TOP_LEFT
        label.isWrapText = true
        return PaneBuilder().setPrefHeight(320.0).setChildren(arrayListOf(label)).build()
    }

    private fun defineVersion() {
        val properties = Properties()
        properties.load(this.javaClass.classLoader.getResourceAsStream("app.properties"))
        version = properties.getProperty("version")
    }

    private fun defineButtonBar() {
        btnStart = ButtonBuilder().setText(getText("screenstart.btnstart")).build()
        btnExit = ButtonBuilder().setText(getText("shared.btnexit")).build()
        buttonBar = ButtonBarBuilder().setButtons(arrayListOf(btnStart, btnExit)).build()
    }

    private fun createImage(): ImageView {
        val image = Image("img/cycle-img.jpg")
        val imageView = ImageView(image)
        imageView.fitWidth = 360.0
        imageView.fitHeight =360.0
        imageView.isPickOnBounds = true
        imageView.isPreserveRatio = true
        return imageView
    }


    private fun createTitlePane(): Pane {
        return PaneBuilder()
            .setPrefWidth(width)
            .setPrefHeight(TITLE_HEIGHT)
            .setStyleClass(STYLE_TITLE_PANE)
            .setChildren(arrayListOf(
                LabelBuilder()
                    .setText(getText("screenstart.title") + " " + version)
                    .setPrefWidth(width)
                    .setStyleClass(STYLE_TITLE_TEXT)
                    .build()))
            .build()
    }

    private fun defineMenuBar() {
        val menuLanguage = Menu(getText("screenstart.menulang"))
        miLanguageDu = MenuItem(getText("screenstart.milangdu"))
        miLanguageEn = MenuItem(getText("screenstart.milangen"))
        menuLanguage.items.addAll(miLanguageDu, miLanguageEn)
        val menuCycles = Menu(getText("screenstart.menucycles"))
        miCycles =  MenuItem(getText("screenstart.micyclestart"))
        menuCycles.items.addAll(miCycles)
        val menuHelp = Menu(getText("screenstart.menuhelp"))
        miUserManual = MenuItem(getText("screenstart.miusermanual"))
        miAbout = MenuItem(getText("screenstart.miabout"))
        menuHelp.items.addAll(miUserManual, miAbout)
        menuBar = MenuBar(menuLanguage, menuCycles, menuHelp)
    }

    private fun defineListeners() {
        miUserManual.onAction = EventHandler { onUserManual()}
        miAbout.onAction = EventHandler { onAbout() }
        miLanguageDu.onAction = EventHandler { onChangeLanguage("du")}
        miLanguageEn.onAction = EventHandler { onChangeLanguage("en")}
        miCycles.onAction = EventHandler { onCycles() }
        btnStart.onAction = EventHandler { onCycles()}
        btnExit.onAction = EventHandler{ Platform.exit() }
    }

    private fun defineLanguage() {
        language = languageManager.getCurrentLanguage()
        Rosetta.setLanguage(language)
    }

    private fun onChangeLanguage(lang: String) {
        language = lang
        languageManager.setCurrentLanguage(language)
        Rosetta.setLanguage(language)
        stage.close()
        stage = Stage()
        initialize()
    }

    private fun onCycles() {
        screenInput.show()
    }

    private fun onAbout() {
        screenAbout.show(version)
    }

    private fun onUserManual() {
        screenManual.show()
    }


}