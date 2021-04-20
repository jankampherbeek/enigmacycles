/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.jfoenix.controls.*
import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.helpers.CycleResultConverter
import com.radixpro.enigma.cycles.helpers.DateValidator
import com.radixpro.enigma.cycles.helpers.Help
import com.radixpro.enigma.cycles.helpers.InfoLabelBuilder
import com.radixpro.enigma.cycles.process.CycleRequestCalculator
import com.radixpro.enigma.cycles.ui.UiDictionary.GAP
import com.radixpro.enigma.cycles.ui.UiDictionary.INPUT_DEFAULT_STYLE
import com.radixpro.enigma.cycles.ui.UiDictionary.INPUT_ERROR_STYLE
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLESHEET
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_SUBTITLE_PANE
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_SUBTITLE_TEXT
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_TITLE_PANE
import com.radixpro.enigma.cycles.ui.UiDictionary.STYLE_TITLE_TEXT
import com.radixpro.enigma.cycles.ui.UiDictionary.SUBTITLE_HEIGHT
import com.radixpro.enigma.cycles.ui.UiDictionary.TITLE_HEIGHT
import com.radixpro.enigma.libfe.fxbuilders.*
import com.radixpro.enigma.libfe.texts.Rosetta
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import java.util.*
import javafx.scene.control.*
import javafx.scene.paint.Color
import org.controlsfx.control.CheckComboBox
import java.lang.Integer.max


class ScreenInput(private val dateValidator: DateValidator,
                  private val calculator: CycleRequestCalculator,
                  private val converter: CycleResultConverter,
                  private val screenLineChart: ScreenLineChart) {

    private val height = 800.0
    private val width = 800.0
    private val halfwidth = 390.0
    private val spaceheight = 18.0
    private val allCelPoints = mutableListOf<UiCelPoints>()
    private val allCelPointNames = mutableListOf<String>()
    private val allAyanamshas = mutableListOf<UiAyanamsha>()
    private val allAyanamshaNames = mutableListOf<String>()
    private var statusComplete = false

    private lateinit var version: String
    private lateinit var txtTitle: String

    private lateinit var btnCalculate: JFXButton
    private lateinit var btnClose: JFXButton
    private lateinit var btnHelp: JFXButton
    private lateinit var buttonBar: ButtonBar
    private lateinit var cbAyanamsha: JFXComboBox<String>
    private lateinit var ccbCelPoints: CheckComboBox<String>
    private lateinit var ccbCelPointsAdd: CheckComboBox<String>
    private lateinit var ccbCelPointSubtract: CheckComboBox<String>

    private lateinit var lblAyanamsha: Label
    private lateinit var lblCelPoints: Label
    private lateinit var lblCelPointsAdd: Label
    private lateinit var lblCelPointsSubtract: Label
    private lateinit var lblObserverPos: Label

    private lateinit var tbCalendar: JFXToggleButton
    private lateinit var tbCycleType: JFXToggleButton
    private lateinit var tbObserverPos: JFXToggleButton
    private lateinit var tbZodiac: JFXToggleButton

    private lateinit var rbCoordinateDecl: JFXRadioButton
    private lateinit var rbCoordinateLat: JFXRadioButton
    private lateinit var rbCoordinateLon: JFXRadioButton
    private lateinit var rbCoordinateRa: JFXRadioButton

    private lateinit var tfEndDate: JFXTextField
    private lateinit var tfInterval: JFXTextField
    private lateinit var tfStartDate: JFXTextField

    private lateinit var tgCoordinate: ToggleGroup
    private lateinit var stage: Stage


    fun show() {
        initialize()
        stage = Stage()
        stage.minHeight = height
        stage.minWidth = width
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = txtTitle
        stage.scene = Scene(createGridPane())
        stage.show()
    }

    private fun initialize() {
        defineVersion()
        txtTitle = getText("screeninput.title") + " " + version
        defineLabels()
        defineAyanamshas()
        defineCelPoints()
        defineCheckComboBoxes()
        defineComboBoxes()
        defineRadioButtons()
        defineTextFields()
        defineButtonBar()
        defineListeners()
        checkStatus()
    }

    private fun defineVersion() {
        val properties = Properties()
        properties.load(this.javaClass.classLoader.getResourceAsStream("app.properties"))
        version = properties.getProperty("version")
    }

    private fun createGridPane(): GridPane {
        val grid = GridPaneBuilder()
            .setHGap(GAP)
            .setPrefWidth(width)
            .setPrefHeight(height)
            .setPadding(Insets(GAP))
            .setStyleSheet(STYLESHEET)
            .build()
        grid.add(defineTitlePane(),0, 0, 1, 1)
        grid.add(defineScrollPane(), 0, 1, 1, 1)
        grid.add(PaneBuilder().setPrefHeight(spaceheight * 2).build(), 0, 2, 1, 1)
        grid.add(buttonBar, 0, 3, 1, 1)
        return grid
    }

    private fun defineTitlePane(): Pane {
        return PaneBuilder()
            .setPrefWidth(width)
            .setPrefHeight(TITLE_HEIGHT)
            .setStyleClass(STYLE_TITLE_PANE)
            .setChildren(arrayListOf(
                LabelBuilder()
                    .setText(txtTitle)
                    .setPrefWidth(width)
                    .setStyleClass(STYLE_TITLE_TEXT)
                    .build()))
            .build()
    }

    private fun defineSubTitlePane(subTitle: String): Pane {
        return PaneBuilder()
            .setPrefWidth(width)
            .setPrefHeight(SUBTITLE_HEIGHT)
            .setStyleClass(STYLE_SUBTITLE_PANE)
            .setChildren(arrayListOf(
                LabelBuilder()
                    .setText(subTitle)
                    .setPrefWidth(width)
                    .setStyleClass(STYLE_SUBTITLE_TEXT)
                    .build()))
            .build()
    }

    private fun defineScrollPane(): ScrollPane {
        val scrollPane = ScrollPane()
        scrollPane.prefHeight=height - 120.0
        scrollPane.prefViewportHeight = height - 140.0
        scrollPane.content = defineInnerGridPane()
        return scrollPane
    }

    private fun defineInnerGridPane(): GridPane {
        val innerGrid = GridPaneBuilder()
            .setHGap(GAP)
            .setPrefWidth(width - 20.0)
            .setPadding(Insets(GAP))
            .setStyleSheet(STYLESHEET)
            .build()
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infocycletype")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 0, 1, 2)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.rbsinglepoints")).build(), 0, 0, 1, 1)
        innerGrid.add(tbCycleType, 1, 0, 1, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 2, 3, 1)
        innerGrid.add(lblCelPoints, 0, 3, 2, 1)
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infocelpoints")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 3, 1, 2)
        innerGrid.add(ccbCelPoints, 0, 4, 2, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 5, 3, 1)
        innerGrid.add(lblCelPointsAdd, 0, 6, 2, 1)
        innerGrid.add(ccbCelPointsAdd, 0, 7, 2, 1)
        innerGrid.add(lblCelPointsSubtract, 0, 8, 2, 1)
        innerGrid.add(ccbCelPointSubtract, 0, 9, 2, 1)
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infocelpointaddsubtract")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 6, 1, 4)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 11, 3, 1)
        innerGrid.add(defineSubTitlePane(getText("screeninput.subtitleastronpos")), 0, 12, 3, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 13, 3, 1)

        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infozodiac")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 14, 1, 3)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.rbzodiactropical")).build(), 0, 15, 1, 1)
        innerGrid.add(tbZodiac, 1, 15, 2, 1)

        innerGrid.add(lblAyanamsha, 0, 16, 2, 1)
        innerGrid.add(cbAyanamsha, 0, 17, 2, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 18, 3, 1)

        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infoobserverpos")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 19, 1, 2)
        innerGrid.add(lblObserverPos, 0, 19, 1, 1)
        innerGrid.add(tbObserverPos, 1, 19, 1, 1)

        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 21, 3, 1)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.lblcoordinate")).build(), 0, 22, 2, 1)
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infocoordinates")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 22, 1, 3)
        innerGrid.add(rbCoordinateLon, 0, 23, 1, 1)
        innerGrid.add(rbCoordinateLat, 1, 23, 1, 1)
        innerGrid.add(rbCoordinateRa, 0, 24, 1, 1)
        innerGrid.add(rbCoordinateDecl, 1, 24, 1, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 25, 3, 1)
        innerGrid.add(defineSubTitlePane(getText("screeninput.subtitleperiod")), 0, 26, 3, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 27, 3, 1)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.lblstartdate")).build(), 0, 28, 2, 1)
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infoperiod")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 28, 1, 5)
        innerGrid.add(tfStartDate, 0, 29, 2, 1)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.lblenddate")).build(), 0, 30, 2, 1)
        innerGrid.add(tfEndDate, 0, 31, 2, 1)
        innerGrid.add(LabelBuilder().setText(getText("shared.calendargreg")).build(), 0, 33, 1, 1)
        innerGrid.add(tbCalendar, 1, 33, 1, 1)
        innerGrid.add(PaneBuilder().setPrefHeight(spaceheight).build(), 0, 34, 3, 1)
        innerGrid.add(LabelBuilder().setText(getText("screeninput.lblinterval")).build(), 0, 35, 2, 1)
        innerGrid.add(InfoLabelBuilder().setText(getText("screeninput.infointerval")).setPrefWidth(halfwidth)
            .setMaxWidth(halfwidth).build(), 2, 35, 1, 2)
        innerGrid.add(tfInterval, 0, 36, 1, 1)
        return innerGrid
    }

    private fun defineCelPoints() {
        for (celPoint in UiCelPoints.values()) {
            allCelPoints.add(celPoint)
            allCelPointNames.add(getText(celPoint.rbKey))
        }
    }

    private fun defineAyanamshas() {
        for (ayanamsha in UiAyanamsha.values()) {
            allAyanamshas.add(ayanamsha)
            allAyanamshaNames.add(getText(ayanamsha.rbKey))
        }
    }

    private fun defineLabels() {
        lblAyanamsha = LabelBuilder().setText(getText("screeninput.lblayanamsha")).build()
        lblCelPoints = LabelBuilder().setText(getText("screeninput.lblcelpoints")).build()
        lblCelPointsAdd = LabelBuilder().setText(getText("screeninput.lblcelpointsadd")).build()
        lblCelPointsSubtract = LabelBuilder().setText(getText("screeninput.lblcelpointssubtract")).build()
        lblObserverPos = LabelBuilder().setText(getText("screeninput.rbobserverposgeo")).build()
    }

    private fun defineTextFields() {
        tfStartDate = JFXTextField()
        tfEndDate = JFXTextField()
        tfInterval = JFXTextField()
        tfInterval.prefWidth = 50.0
        tfInterval.maxWidth = 100.0
    }


    private fun defineCheckComboBoxes() {
        ccbCelPoints = CheckComboBox()
        ccbCelPointsAdd = CheckComboBox()
        ccbCelPointSubtract = CheckComboBox()
        ccbCelPoints.items.addAll(allCelPointNames)
        ccbCelPointsAdd.items.addAll(allCelPointNames)
        ccbCelPointSubtract.items.addAll(allCelPointNames)
    }

    private fun defineComboBoxes() {
        cbAyanamsha = JFXComboBox()
        cbAyanamsha.items.addAll(allAyanamshaNames)
    }

    private fun defineRadioButtons() {
        tbCalendar = JFXToggleButton()
        tbCalendar.text = getText("shared.calendarjul")
        tbCalendar.toggleColor = Color.STEELBLUE
        tbCalendar.toggleLineColor=Color.LIGHTSTEELBLUE
        tbCycleType = JFXToggleButton()
        tbCycleType.text = getText("screeninput.rbsumofpoints")
        tbCycleType.toggleColor = Color.STEELBLUE
        tbCycleType.toggleLineColor = Color.LIGHTSTEELBLUE

        tgCoordinate = ToggleGroup()
        rbCoordinateDecl = JFXRadioButton(getText("screeninput.rbcoordinatedecl"))
        rbCoordinateLat = JFXRadioButton(getText("screeninput.rbcoordinatelat"))
        rbCoordinateLon = JFXRadioButton(getText("screeninput.rbcoordinatelon"))
        rbCoordinateRa = JFXRadioButton(getText("screeninput.rbcoordinatera"))
        rbCoordinateDecl.selectedColor = Color.STEELBLUE
        rbCoordinateDecl.unSelectedColor = Color.LIGHTSTEELBLUE
        rbCoordinateRa.selectedColor = Color.STEELBLUE
        rbCoordinateRa.unSelectedColor = Color.LIGHTSTEELBLUE
        rbCoordinateLon.selectedColor = Color.STEELBLUE
        rbCoordinateLon.unSelectedColor = Color.LIGHTSTEELBLUE
        rbCoordinateLat.selectedColor = Color.STEELBLUE
        rbCoordinateLat.unSelectedColor = Color.LIGHTSTEELBLUE

        rbCoordinateLon.isSelected = true
        rbCoordinateLon.toggleGroup = tgCoordinate
        rbCoordinateDecl.toggleGroup = tgCoordinate
        rbCoordinateLat.toggleGroup = tgCoordinate
        rbCoordinateRa.toggleGroup = tgCoordinate
        tbObserverPos = JFXToggleButton()
        tbObserverPos.text = getText("screeninput.rbobserverposhelio")
        tbObserverPos.toggleColor = Color.STEELBLUE
        tbObserverPos.toggleLineColor = Color.LIGHTSTEELBLUE
        tbZodiac = JFXToggleButton()
        tbZodiac.text = getText("screeninput.rbzodiacsidereal")
        tbZodiac.toggleColor = Color.STEELBLUE
        tbZodiac.toggleLineColor=Color.LIGHTSTEELBLUE
    }

    private fun defineButtonBar() {
        btnHelp = JFXButton(getText("shared.btnhelp"))
        btnClose = JFXButton(getText("shared.btn_close"))
        btnCalculate = JFXButton(getText("screeninput.btncalculate"))
        btnCalculate.isDisable = true
        btnCalculate.isFocusTraversable = false
        buttonBar = ButtonBarBuilder().setButtons(arrayListOf(btnCalculate, btnHelp, btnClose)).build()
    }

    private fun defineListeners() {
        tbCycleType.selectedProperty().addListener{ _, _, _ -> checkStatus()  }
        tbZodiac.selectedProperty().addListener{ _, _, _ -> checkStatus()  }
        tbObserverPos.selectedProperty().addListener{ _, _, _ -> checkStatus()  }
        tgCoordinate.selectedToggleProperty().addListener { _: ObservableValue<out Toggle?>?, _: Toggle?, _: Toggle? -> checkStatus()  }
        tbCalendar.selectedProperty().addListener{ _, _, _ -> checkStatus()  }

        tfStartDate.textProperty().addListener { _, _, _ -> checkStatus() }
        tfEndDate.textProperty().addListener { _, _, _ -> checkStatus() }
        tfInterval.textProperty().addListener { _, _, _ -> checkStatus() }

        btnClose.onAction = EventHandler { stage.close() }
        btnHelp.onAction = EventHandler { onHelp() }
        btnCalculate.onAction = EventHandler { onCalculate() }
    }

    private fun checkStatus() {
        disEnableCycleType(!tbCycleType.isSelected)
        disEnableCenter(tgCoordinate.selectedToggle == rbCoordinateRa
                || tgCoordinate.selectedToggle == rbCoordinateDecl)
        disEnableEquatorial(tbObserverPos.isSelected)
        disEnableAyanamsha(!tbZodiac.isSelected)
        statusComplete = defineStatusComplete()
        btnCalculate.isDisable = !statusComplete
        btnCalculate.isFocusTraversable = statusComplete
    }

    private fun disEnableCycleType(typeSingle: Boolean) {
        lblCelPointsAdd.isDisable = typeSingle
        lblCelPointsSubtract.isDisable = typeSingle
        ccbCelPointsAdd.isDisable = typeSingle
        ccbCelPointSubtract.isDisable = typeSingle
        lblCelPoints.isDisable = !typeSingle
        ccbCelPoints.isDisable = !typeSingle
    }

    private fun disEnableCenter(equatorial: Boolean) {
        lblObserverPos.isDisable = equatorial
        tbObserverPos.isDisable = equatorial
    }

    private fun disEnableEquatorial(helio: Boolean) {
        rbCoordinateRa.isDisable = helio
        rbCoordinateDecl.isDisable = helio
    }

    private fun disEnableAyanamsha(tropical: Boolean) {
        lblAyanamsha.isDisable = tropical
        cbAyanamsha.isDisable = tropical
    }

    private fun defineStatusComplete(): Boolean {
        var statusFound = true
          if (tbCycleType.isSelected) {
              if (ccbCelPointsAdd.checkModel.checkedIndices.size == 0
                 && ccbCelPointSubtract.checkModel.checkedIndices.size == 0) statusFound = false
          } else {
              if (ccbCelPoints.checkModel.checkedIndices.size == 0) statusFound = false
          }
        if (tbZodiac.isSelected && cbAyanamsha.selectionModel.isEmpty)  statusFound = false
        if (!validateDates()) statusFound = false
        if (!validateInterval()) statusFound = false
        return statusFound
    }

    private fun validateDates(): Boolean {
        var allDatesOk = true
        var dateOk = dateValidator.checkDate(tfStartDate.text, tbCalendar.isSelected)
        if (tfStartDate.text.isBlank()) allDatesOk = false
        else if (!dateOk) {
            tfStartDate.style = INPUT_ERROR_STYLE
            allDatesOk = false
        } else tfStartDate.style = INPUT_DEFAULT_STYLE

        dateOk = dateValidator.checkDate(tfEndDate.text, tbCalendar.isSelected)
        if (tfEndDate.text.isBlank()) allDatesOk = false
        else if (!dateOk) {
            tfEndDate.style = INPUT_ERROR_STYLE
            allDatesOk = false
        } else tfEndDate.style = INPUT_DEFAULT_STYLE
        return allDatesOk
    }

    private fun validateInterval(): Boolean {
        if (tfInterval.text.isBlank()) return false
        try {
            tfInterval.style = INPUT_DEFAULT_STYLE
        } catch(e: Exception) {
            tfInterval.style = INPUT_ERROR_STYLE
            return false
        }
        return true
    }

    private fun onHelp() {
        Help(
            Rosetta.getHelpText("help.screeninput.title"),
            Rosetta.getHelpText("help.screeninput.content")
        ).showContent()
    }

    private fun onCalculate() {
        val cycleSettings = defineCycleSettings()
        // TODO dit is een tijdelijke oplossing, aparte controller maken
        val tsValues = calculator.calculateCycleRequest(cycleSettings)
        val presResult = converter.responseSingleToPresResult(cycleSettings, tsValues)
        screenLineChart.show(presResult)
    }

    private fun defineCycleSettings(): CycleDefinition {
        val cycleCoordinateType = when(tgCoordinate.selectedToggle) {
            rbCoordinateLon  -> CycleCoordinateTypes.LONGITUDE
            rbCoordinateLat -> CycleCoordinateTypes.LATITUDE
            rbCoordinateRa -> CycleCoordinateTypes.RIGHT_ASCENSION
            else -> CycleCoordinateTypes.DECLINATION
        }
        val zodiac = if (tbZodiac.isSelected) Zodiac.SIDEREAL else Zodiac.TROPICAL
        val ayanamshaIndex = max(1, cbAyanamsha.selectionModel.selectedIndex)   // use Fagan ayanamsha if none is defined, for tropical this will be ignored
        val ayanamsha = allAyanamshas[ayanamshaIndex]
        val cycleCoordinates = CycleCoordinates(cycleCoordinateType, zodiac, ayanamsha)
        val cyclePeriod = CyclePeriod(tfStartDate.text, tfEndDate.text, tfInterval.text.toDouble(), true)
        val center = if (tbObserverPos.isSelected) Center.HELIOCENTRIC else Center.GEOCENTRIC
        val cycleType = if (tbCycleType.isSelected) CycleType.SUM_OF_POINTS else CycleType.SINGLE_POINT
        val celPoints = mutableListOf<UiCelPoints>()
        for (cpIndex in ccbCelPoints.checkModel.checkedIndices) celPoints.add(allCelPoints[cpIndex])
        val summableCelPoints = mutableListOf<SummableCelPoint>()
        for (cpIndex in ccbCelPointsAdd.checkModel.checkedIndices) summableCelPoints.add(SummableCelPoint(allCelPoints[cpIndex], true))
        for (cpIndex in ccbCelPointSubtract.checkModel.checkedIndices) summableCelPoints.add(SummableCelPoint(allCelPoints[cpIndex], false))
        return CycleDefinition(cycleCoordinates, center, cycleType, celPoints, summableCelPoints, cyclePeriod)
    }

}