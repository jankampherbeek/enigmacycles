/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.jfoenix.controls.JFXCheckBox
import com.radixpro.enigma.cycles.core.CpSelectModus
import com.radixpro.enigma.cycles.ui.UiDictionary.GAP
import com.radixpro.enigma.libbe.domain.ObserverPos
import com.radixpro.enigma.libfe.core.CelPointCat
import com.radixpro.enigma.libfe.core.UiCelPoints
import com.radixpro.enigma.libfe.fragments.Titles
import com.radixpro.enigma.libfe.fxbuilders.*
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage


enum class CPSelectionMessages {
    OK_CLICKED,
    CANCEL_CLICKED,
    HELP_CLICKED
}


/**
 * Model for ViewCPSelection: Selection of Celestial Points.
 */
object ModelCPSelection {

    lateinit var txtTitle: String
    var cpChoicesClassic = mutableListOf<JFXCheckBox>()
    var cpChoicesModern = mutableListOf<JFXCheckBox>()
    var cpChoicesMathPoint = mutableListOf<JFXCheckBox>()
    var cpChoicesCentaur = mutableListOf<JFXCheckBox>()
    var cpChoicesPlutoid = mutableListOf<JFXCheckBox>()
    var cpChoicesAsteroid = mutableListOf<JFXCheckBox>()
    var cpItems = mutableListOf<UiCelPoints>()
    var selectedCelPoints = mutableListOf<UiCelPoints>()

    var cpCelpointsClassic = mutableListOf<UiCelPoints>()
    var cpCelpointsModern = mutableListOf<UiCelPoints>()
    var cpCelpointsMathPoint = mutableListOf<UiCelPoints>()
    var cpCelpointsCentaur = mutableListOf<UiCelPoints>()
    var cpCelpointsPlutoid = mutableListOf<UiCelPoints>()
    var cpCelpointsAsteroid = mutableListOf<UiCelPoints>()

}


/**
 * Controller for ViewCPSelection: Selection of Celestial Points.
 */
class ControllerCPSelection {

    private var useGeocentric = true

    var selectModus = CpSelectModus(
        ObserverPos.GEOCENTRIC,
        listOf(CelPointCat.CLASSIC, CelPointCat.MODERN, CelPointCat.MATH_POINT),
        true,
        0.0,
        0.0
    )

    init {
        setUp()
    }

    private fun setUp() {
        ModelCPSelection.txtTitle = getText("shr.cpselection.title")
        useGeocentric = selectModus.observerPos == ObserverPos.GEOCENTRIC || selectModus.observerPos == ObserverPos.TOPOCENTRIC
        defineCelestialPoints()
    }

    private fun defineCelestialPoints() {
        for (celPoint in UiCelPoints.values()) {
            if (checkForInclusion(celPoint)) {
                ModelCPSelection.cpItems.add(celPoint)
                when (celPoint.category) {
                    CelPointCat.CLASSIC -> {
                        ModelCPSelection.cpChoicesClassic.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsClassic.add(celPoint)
                    }
                    CelPointCat.MODERN -> {
                        ModelCPSelection.cpChoicesModern.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsModern.add(celPoint)
                    }
                    CelPointCat.MATH_POINT -> {
                        ModelCPSelection.cpChoicesMathPoint.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsMathPoint.add(celPoint)
                    }
                    CelPointCat.CENTAUR -> {
                        ModelCPSelection.cpChoicesCentaur.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsCentaur.add(celPoint)
                    }
                    CelPointCat.PLUTOID -> {
                        ModelCPSelection.cpChoicesPlutoid.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsPlutoid.add(celPoint)
                    }
                    else -> {
                        ModelCPSelection.cpChoicesAsteroid.add(CheckBoxBuilder().setText(getText(celPoint.rbKey)).build())
                        ModelCPSelection.cpCelpointsAsteroid.add(celPoint)
                    }
                }
            }
        }
    }

    private fun checkForInclusion(celPoint: UiCelPoints): Boolean {
        // todo add check for period
        return ((celPoint.geo && useGeocentric) || (celPoint.helio && !useGeocentric))
    }

    fun handleMessage(msg: CPSelectionMessages) {
        when (msg) {
            CPSelectionMessages.OK_CLICKED -> onOk()
            CPSelectionMessages.CANCEL_CLICKED -> onCancel()
        }
    }

    private fun onOk() {
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesClassic, ModelCPSelection.cpCelpointsClassic)
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesModern, ModelCPSelection.cpCelpointsModern)
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesMathPoint, ModelCPSelection.cpCelpointsMathPoint)
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesCentaur, ModelCPSelection.cpCelpointsCentaur)
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesPlutoid, ModelCPSelection.cpCelpointsPlutoid)
        checkSubRangeOfCelpoints(ModelCPSelection.cpChoicesAsteroid, ModelCPSelection.cpCelpointsAsteroid)

    }

    private fun checkSubRangeOfCelpoints(checkBoxes: List<JFXCheckBox>, cpRange: List<UiCelPoints> ){
        for (i in checkBoxes.indices) {
            if (checkBoxes[i].isSelected)
                ModelCPSelection.selectedCelPoints.add(cpRange[i])
        }
    }


    private fun onCancel() {
        // clear selections
        // close screen
    }

}

/**
 * View for selection of Celestial Points.
 */
class ViewCPSelection(private val controller: ControllerCPSelection) {
    val width = 400.0
    private val height = 700.0
    private val halfWidth = width / 2.0 - 20
    val stage = Stage()
    private val btnOk = ButtonBuilder().setText(getText("shr.btnok")).build()
    private val btnCancel = ButtonBuilder().setText(getText("shr.btncancel")).build()
    private val btnHelp = ButtonBuilder().setText(getText("shr.btnhelp")).build()

    fun show() {
        initialize()
        stage.minHeight = height
        stage.minWidth = width
        stage.title = ModelCPSelection.txtTitle
        stage.scene = Scene(createMainVBox())
        stage.showAndWait()
    }

    private fun initialize() {
        ModelCPSelection.selectedCelPoints.clear()
        btnOk.onAction = EventHandler{
            controller.handleMessage(CPSelectionMessages.OK_CLICKED)
            stage.close()
        }
    }


    private fun createMainVBox(): VBox {
        val title = Titles.createTitlePane(ModelCPSelection.txtTitle, width)
        val buttonBar = ButtonBarBuilder().setButtons(btnOk, btnCancel, btnHelp).build()
        return VBoxBuilder().setPrefWidth(width).setPadding(Insets(GAP)).setChildren(title, createCpHBox(), buttonBar).build()
    }

    private fun createCpHBox(): HBox {
        return HBoxBuilder().setPrefWidth(width).setChildren(createCPLeftGridPane(), createCPRightGridPane()).build()
    }


    private fun createCPLeftGridPane(): GridPane {
        var rowId = 0
        val grid = GridPaneBuilder()
            .setHGap(UiDictionary.GAP)
            .setPrefWidth(width)
            .setPrefHeight(height)
            .setPadding(Insets(UiDictionary.GAP))
            .setStyleSheet(UiDictionary.STYLESHEET)
            .build()
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.CLASSIC.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesClassic) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.MODERN.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesModern) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.MATH_POINT.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesMathPoint) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.CENTAUR.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesCentaur) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        return grid
    }

    private fun createCPRightGridPane(): GridPane {
        var rowId = 0
        val grid = GridPaneBuilder()
            .setHGap(UiDictionary.GAP)
            .setPrefWidth(width)
            .setPrefHeight(height)
            .setPadding(Insets(UiDictionary.GAP))
            .setStyleSheet(UiDictionary.STYLESHEET)
            .build()
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.PLUTOID.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesPlutoid) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Titles.createSubTitlePane(getText(CelPointCat.ASTEROID.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesAsteroid) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        return grid
    }




}