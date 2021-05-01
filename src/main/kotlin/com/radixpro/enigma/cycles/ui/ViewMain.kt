/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXTextField
import com.jfoenix.controls.JFXToggleButton
import com.radixpro.enigma.cycles.core.CelPointCat
import com.radixpro.enigma.cycles.core.CpSelectModus
import com.radixpro.enigma.cycles.helpers.ButtonBuilder
import com.radixpro.enigma.cycles.helpers.ComboBoxBuilder
import com.radixpro.enigma.cycles.helpers.TextFieldBuilder
import com.radixpro.enigma.libbe.domain.ObserverPos
import com.radixpro.enigma.libfe.fxbuilders.GridPaneBuilder
import com.radixpro.enigma.libfe.fxbuilders.LabelBuilder
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage

/**
 * View for the main screen (input and menu).
 */
class ViewMain(private val controller: ControllerMain) {

    private val height = 800.0
    val width = 460.0
    private val halfWidth = width / 2  - 20.0
    private val spaceheight = 18.0
    private lateinit var comboAyanamsha: JFXComboBox<String>
    private lateinit var comboCoordinates: JFXComboBox<String>
    private lateinit var comboCycleType: JFXComboBox<String>

    private lateinit var btnCPInput: JFXButton
    private lateinit var lblAyanamsha: Label
    private lateinit var lblCelPoints: Label
    private lateinit var lblCoordinate: Label
    private lateinit var lblCycleType: Label
    private lateinit var lblEndDate: Label
    private lateinit var lblInterval: Label
    private lateinit var lblStartDate: Label
    private lateinit var mbGeneral: MenuBar
    private lateinit var menuExport: Menu
    private lateinit var menuGeneral: Menu
    private lateinit var menuHelp: Menu
    private lateinit var miLangEn: MenuItem
    private lateinit var miLangDu: MenuItem
    private lateinit var miExit: MenuItem
    private lateinit var miExportGraph: MenuItem
    private lateinit var miExportData: MenuItem
    private lateinit var miHelpAbout: MenuItem
    private lateinit var miHelpManual: MenuItem
    private lateinit var tbCalendar: JFXToggleButton
    private lateinit var tfEndDate: JFXTextField
    private lateinit var tfInterval: JFXTextField
    private lateinit var tfStartDate: JFXTextField
    lateinit var gridPane: GridPane
    private val stage = Stage()

    private val viewCPSelection = ViewCPSelection(ControllerCPSelection())

    fun show() {
        initialize()
        stage.minHeight = height
        stage.minWidth = width
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = ModelMain.txtTitle
        stage.scene = Scene(createGridPane())
        stage.show()
    }

    private fun initialize() {
        defineMenu()
        defineLabels()
        defineButtons()
        defineTextFields()
        defineToggleButtons()
        populateComboBoxes()
    }

    private fun onCPInput() {
        val selectModus = CpSelectModus(ObserverPos.GEOCENTRIC,
            listOf(CelPointCat.CLASSIC, CelPointCat.MODERN),
            true,
            2000000.5,
            3000000.5)
        viewCPSelection.show(selectModus)
    }

    private fun defineMenu() {
        miExit = MenuItem(getText("menu.general.exit"))
        miLangEn = MenuItem(getText("menu.general.en"))
        miLangDu = MenuItem(getText("menu.general.du"))
        miExportGraph = MenuItem(getText("menu.export.graph"))
        miExportData = MenuItem(getText("menu.export.data"))
        miHelpAbout = MenuItem(getText("menu.help.about"))
        miHelpManual = MenuItem(getText("menu.help.manual"))
        menuGeneral = Menu(getText("menu.general"))
        menuExport = Menu(getText("menu.export"))
        menuHelp = Menu(getText("menu.help"))
        menuGeneral.items.addAll(miLangEn, miLangDu, miExit)
        menuExport.items.addAll(miExportGraph, miExportData)
        menuHelp.items.addAll(miHelpAbout, miHelpManual)
        mbGeneral = MenuBar(menuGeneral, menuExport, menuHelp)
    }


    private fun defineLabels() {
        lblEndDate = LabelBuilder().setText(getText("input.lblenddate")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblStartDate = LabelBuilder().setText(getText("input.lblstartdate")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblInterval = LabelBuilder().setText(getText("input.lblinterval")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblCycleType = LabelBuilder().setText(getText("input.lblcycletype")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblCelPoints = LabelBuilder().setText(getText("input.lblcelpoints")).setPrefHeight(50.0).setAlignment(Pos.BASELINE_LEFT).build()
        lblAyanamsha = LabelBuilder().setText(getText("input.lblayanamsha")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblCoordinate = LabelBuilder().setText(getText("input.lblcoordinate")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()

    }

    private fun defineButtons() {
        btnCPInput = ButtonBuilder().setText(getText("input.btncpinput")).setPrefWidth(halfWidth).setAlignment(Pos.BOTTOM_RIGHT).build()
        btnCPInput.onAction = EventHandler{ onCPInput()}
    }

    private fun defineToggleButtons() {
        tbCalendar = JFXToggleButton()
        tbCalendar.text = getText("input.tbcalendar")
        tbCalendar.toggleColor = Color.STEELBLUE
        tbCalendar.toggleLineColor = Color.LIGHTSTEELBLUE

    }

    private fun populateComboBoxes() {
        comboCoordinates = ComboBoxBuilder().setPrefWidth(width).setItems(ModelMain.allCoordinateNames).build()
        comboCycleType = ComboBoxBuilder().setPrefWidth(width).setItems(ModelMain.allCycleTypeNames).build()
        comboAyanamsha = ComboBoxBuilder().setPrefWidth(width).setItems(ModelMain.allAyanamshaNames).build()
        comboCoordinates.selectionModel.select(0)   // default select first value
        comboCycleType.selectionModel.select(0)
        comboAyanamsha.selectionModel.select(0)
    }

    private fun defineTextFields() {
        tfStartDate = TextFieldBuilder().setPrefWidth(halfWidth).setPromptText(getText("lshr.promptdate")).build()
        tfEndDate = TextFieldBuilder().setPrefWidth(halfWidth).setPromptText(getText("lshr.promptdate")).build()
        tfInterval = TextFieldBuilder().setPrefWidth(50.0).setMaxWidth(100.0).build()
    }


    private fun createGridPane(): GridPane {
        val grid = GridPaneBuilder()
            .setHGap(UiDictionary.GAP)
            .setPrefWidth(width)
            .setPrefHeight(height)
            .setPadding(Insets(UiDictionary.GAP))
            .setStyleSheet(UiDictionary.STYLESHEET)
            .build()
        grid.add(mbGeneral, 0, 0, 2, 1)
        grid.add(Utilities4View.createTitlePane(ModelMain.txtTitle, width), 0, 1, 4, 1)
        grid.add(lblStartDate, 0, 2, 1, 1)
        grid.add(lblEndDate, 1, 2, 1, 1)
        grid.add(tfStartDate, 0, 3, 1, 1)
        grid.add(tfEndDate, 1, 3, 1, 1)
        grid.add(tbCalendar, 0, 4, 2, 1)
        grid.add(lblInterval, 0, 5, 2, 1)
        grid.add(tfInterval, 0, 6, 2, 1)
        grid.add(lblCoordinate, 0, 8, 2, 1)
        grid.add(comboCoordinates, 0, 9, 2, 1)
        grid.add(lblCycleType, 0, 10, 2, 1)
        grid.add(comboCycleType, 0, 11, 2, 1)
        grid.add(lblAyanamsha, 0, 13, 2, 1)
        grid.add(comboAyanamsha, 0, 14, 2, 1)
        grid.add(lblCelPoints, 0, 15, 1, 1)
        grid.add(btnCPInput, 1, 15, 1, 1)
        return grid
    }

    fun asParent(): Parent? {
        return gridPane
    }


}