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
import com.radixpro.enigma.cycles.core.CycleCoordinateTypes
import com.radixpro.enigma.cycles.core.CycleType
import com.radixpro.enigma.cycles.core.UiAyanamsha
import com.radixpro.enigma.cycles.core.UiCelPoints
import com.radixpro.enigma.libfe.fragments.Titles
import com.radixpro.enigma.libfe.fxbuilders.*
import com.radixpro.enigma.libfe.texts.Rosetta
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*

enum class MainMessages {
    CELPOINTS_SELECTED,
    OK_CLICKED,
    EXIT_CLICKED
}

/**
 * Model for ViewMain.
 */
object ModelMain {

    lateinit var selectedCycleType: CycleType
    lateinit var txtTitle: String
    lateinit var subTxtTitleAstron: String
    val allAyanamshas = mutableListOf<UiAyanamsha>()
    val allAyanamshaNames = mutableListOf<String>()
    val allCoordinates = mutableListOf<CycleCoordinateTypes>()
    val allCoordinateNames = mutableListOf<String>()
    val allCycleTypes = mutableListOf<CycleType>()
    val allCycleTypeNames = mutableListOf<String>()
    val allCelPoints = mutableListOf<UiCelPoints>()
    val allCelPointNames = mutableListOf<String>()

    val selectedCelPoints = mutableListOf<UiCelPoints>()
    var selectedCelPointsText = ""



}

/**
 * Controller for ViewMain
 */
class ControllerMain {

    init { setUp()}

    private fun setUp() {
        defineTitle()
        defineCycleTypes()
        defineCelPoints()
        defineAyanamshas()
        defineCoordinates()
        setInitialValues()
    }

    private fun defineTitle() {
        val properties = Properties()
        properties.load(this.javaClass.classLoader.getResourceAsStream("app.properties"))
        ModelMain.txtTitle = Rosetta.getText("input.title") + " " + properties.getProperty("version")
        ModelMain.subTxtTitleAstron = Rosetta.getText("input.subtitleastron")
    }


    private fun defineCycleTypes() {
        ModelMain.allCycleTypes.clear()
        ModelMain.allCycleTypeNames.clear()
        for (cycleType in CycleType.values()) {
            ModelMain.allCycleTypes.add(cycleType)
            ModelMain.allCycleTypeNames.add(Rosetta.getText(cycleType.rbKey))
        }
    }

    private fun defineCelPoints() {
        ModelMain.allCelPoints.clear()
        ModelMain.allCelPointNames.clear()
        for (celPoint in UiCelPoints.values()) {
            ModelMain.allCelPoints.add(celPoint)
        }
    }

    private fun defineAyanamshas() {
        ModelMain.allAyanamshas.clear()
        ModelMain.allAyanamshaNames.clear()
        for (ayanamsha in UiAyanamsha.values()) {
            ModelMain.allAyanamshas.add(ayanamsha)
            ModelMain.allAyanamshaNames.add(Rosetta.getText(ayanamsha.rbKey))
        }
    }

    private fun defineCoordinates() {
        ModelMain.allCoordinates.clear()
        ModelMain.allCoordinateNames.clear()
        for (coordinate in CycleCoordinateTypes.values()) {
            ModelMain.allCoordinates.add(coordinate)
            ModelMain.allCoordinateNames.add(Rosetta.getText(coordinate.rbKey))
        }
    }

    private fun setInitialValues() {
        setSelectedCycleType(CycleType.SINGLE_POINT)
    }

    fun setSelectedCycleType(cycleType: CycleType) {
        ModelMain.selectedCycleType = cycleType
    }

    fun handleMessage(msg: MainMessages) {
        when (msg) {
            MainMessages.OK_CLICKED -> onOk()
            MainMessages.CELPOINTS_SELECTED -> checkSelectedCelPoints()
        }

    }

    private fun onOk() {

    }

    private fun checkSelectedCelPoints() {
        ModelMain.selectedCelPoints.clear()
        ModelMain.selectedCelPoints.addAll(ModelCPSelection.selectedCelPoints)
        ModelMain.selectedCelPointsText = showSelectedCelPoints()
    }

    private fun showSelectedCelPoints(): String {
        var celPointText = ""
        var separator = ""
        val nrOfCPs = ModelMain.selectedCelPoints.size
        for (i in 0 until nrOfCPs) {
            separator = when(i) {
                nrOfCPs -> ""
                0 -> ""
                3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36 -> ",\n"
                else -> ", "
            }
            celPointText += separator + getText(ModelMain.selectedCelPoints[i].rbKey)
        }
        return celPointText
    }

}

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
    private lateinit var lblSelectedCelPoints: Label
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
        viewCPSelection.show()
        controller.handleMessage(MainMessages.CELPOINTS_SELECTED)
        lblSelectedCelPoints.text = ModelMain.selectedCelPointsText
    }

    private fun defineMenu() {
        miExit = MenuItem(Rosetta.getText("menu.general.exit"))
        miLangEn = MenuItem(Rosetta.getText("menu.general.en"))
        miLangDu = MenuItem(Rosetta.getText("menu.general.du"))
        miExportGraph = MenuItem(Rosetta.getText("menu.export.graph"))
        miExportData = MenuItem(Rosetta.getText("menu.export.data"))
        miHelpAbout = MenuItem(Rosetta.getText("menu.help.about"))
        miHelpManual = MenuItem(Rosetta.getText("menu.help.manual"))
        menuGeneral = Menu(Rosetta.getText("menu.general"))
        menuExport = Menu(Rosetta.getText("menu.export"))
        menuHelp = Menu(Rosetta.getText("menu.help"))
        menuGeneral.items.addAll(miLangEn, miLangDu, miExit)
        menuExport.items.addAll(miExportGraph, miExportData)
        menuHelp.items.addAll(miHelpAbout, miHelpManual)
        mbGeneral = MenuBar(menuGeneral, menuExport, menuHelp)
    }


    private fun defineLabels() {
        lblEndDate = LabelBuilder().setText(Rosetta.getText("input.lblenddate")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblStartDate = LabelBuilder().setText(Rosetta.getText("input.lblstartdate")).setPrefHeight(40.0).setAlignment(
            Pos.BOTTOM_LEFT
        ).build()
        lblInterval = LabelBuilder().setText(Rosetta.getText("input.lblinterval")).setPrefHeight(40.0).setAlignment(Pos.BOTTOM_LEFT).build()
        lblCycleType = LabelBuilder().setText(Rosetta.getText("input.lblcycletype")).setPrefHeight(40.0).setAlignment(
            Pos.BOTTOM_LEFT
        ).build()
        lblCelPoints = LabelBuilder().setText(Rosetta.getText("input.lblcelpoints")).setPrefHeight(50.0).setAlignment(
            Pos.BASELINE_LEFT
        ).build()
        lblAyanamsha = LabelBuilder().setText(Rosetta.getText("input.lblayanamsha")).setPrefHeight(40.0).setAlignment(
            Pos.BOTTOM_LEFT
        ).build()
        lblCoordinate = LabelBuilder().setText(Rosetta.getText("input.lblcoordinate")).setPrefHeight(40.0).setAlignment(
            Pos.BOTTOM_LEFT
        ).build()
        lblSelectedCelPoints = Label("")

    }

    private fun defineButtons() {
        btnCPInput = ButtonBuilder().setText(Rosetta.getText("input.btncpinput")).setPrefWidth(halfWidth).setAlignment(
            Pos.BOTTOM_RIGHT
        ).build()
        btnCPInput.onAction = EventHandler{ onCPInput()}
    }

    private fun defineToggleButtons() {
        tbCalendar = JFXToggleButton()
        tbCalendar.text = Rosetta.getText("input.tbcalendar")
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
        tfStartDate = TextFieldBuilder().setPrefWidth(halfWidth).setPromptText(Rosetta.getText("lshr.promptdate")).build()
        tfEndDate = TextFieldBuilder().setPrefWidth(halfWidth).setPromptText(Rosetta.getText("lshr.promptdate")).build()
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
        grid.add(Titles.createTitlePane(ModelMain.txtTitle, width), 0, 1, 4, 1)
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
        grid.add(lblSelectedCelPoints, 0, 16, 2, 1)
        return grid
    }

    fun asParent(): Parent? {
        return gridPane
    }


}