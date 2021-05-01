/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.jfoenix.controls.JFXCheckBox
import com.radixpro.enigma.cycles.core.CelPointCat
import com.radixpro.enigma.cycles.core.CpSelectModus
import com.radixpro.enigma.cycles.core.UiCelPoints
import com.radixpro.enigma.cycles.helpers.ButtonBarBuilder
import com.radixpro.enigma.cycles.helpers.ButtonBuilder
import com.radixpro.enigma.cycles.helpers.CheckBoxBuilder
import com.radixpro.enigma.cycles.ui.UiDictionary.GAP
import com.radixpro.enigma.libbe.domain.ObserverPos
import com.radixpro.enigma.libfe.fxbuilders.GridPaneBuilder
import com.radixpro.enigma.libfe.fxbuilders.HBoxBuilder
import com.radixpro.enigma.libfe.fxbuilders.VBoxBuilder
import com.radixpro.enigma.libfe.texts.Rosetta
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
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
    var selectedCpItems = mutableListOf<UiCelPoints>()
    var selectedCpNames = mutableListOf<String>()
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
        ModelCPSelection.txtTitle = Rosetta.getText("lshr.cpselection.title")
        useGeocentric = selectModus.observerPos == ObserverPos.GEOCENTRIC || selectModus.observerPos == ObserverPos.TOPOCENTRIC
        defineCelestialPoints()
    }

    private fun defineCelestialPoints() {
        for (celPoint in UiCelPoints.values()) {
            if (checkForInclusion(celPoint)) {
                ModelCPSelection.cpItems.add(celPoint)
                when (celPoint.category) {
                    CelPointCat.CLASSIC -> ModelCPSelection.cpChoicesClassic.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
                    CelPointCat.MODERN -> ModelCPSelection.cpChoicesModern.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
                    CelPointCat.MATH_POINT -> ModelCPSelection.cpChoicesMathPoint.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
                    CelPointCat.CENTAUR -> ModelCPSelection.cpChoicesCentaur.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
                    CelPointCat.PLUTOID -> ModelCPSelection.cpChoicesPlutoid.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
                    else -> ModelCPSelection.cpChoicesAsteroid.add(
                        CheckBoxBuilder().setText(getText(celPoint.rbKey)).build()
                    )
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
        }
    }

    private fun onOk() {
        for (checkItem in ModelCPSelection.cpChoicesClassic) {
            // TODO hier nog iets op verzinnen, werken met totaallijst?
//            if (checkItem.isSelected) ModelCPSelection.selectedCpNames.add(checkItem.)
        }
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

    fun show(selectModus: CpSelectModus) {
        initialize(selectModus)
        stage.minHeight = height
        stage.minWidth = width
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.title = ModelCPSelection.txtTitle
        stage.scene = Scene(createMainVBox())
        stage.show()
    }

    private fun initialize(selectModus: CpSelectModus) {
        controller.selectModus = selectModus

    }


    private fun createMainVBox(): VBox {
        val title = Utilities4View.createTitlePane(ModelCPSelection.txtTitle, width)
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
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.CLASSIC.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesClassic) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.MODERN.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesModern) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.MATH_POINT.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesMathPoint) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.CENTAUR.rbKey), halfWidth), 0, rowId++, 1, 1)
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
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.PLUTOID.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesPlutoid) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        grid.add(Utilities4View.createSubTitlePane(getText(CelPointCat.ASTEROID.rbKey), halfWidth), 0, rowId++, 1, 1)
        for (checkBox in ModelCPSelection.cpChoicesAsteroid) {
            grid.add(checkBox, 0, rowId++, 1, 1)
        }
        return grid
    }


}