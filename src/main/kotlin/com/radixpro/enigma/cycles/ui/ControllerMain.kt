/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.cycles.core.CycleCoordinateTypes
import com.radixpro.enigma.cycles.core.CycleType
import com.radixpro.enigma.cycles.core.UiAyanamsha
import com.radixpro.enigma.cycles.core.UiCelPoints
import com.radixpro.enigma.libfe.texts.Rosetta.getText
import java.util.*

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
        ModelMain.txtTitle = getText("input.title") + " " + properties.getProperty("version")
        ModelMain.subTxtTitleAstron = getText("input.subtitleastron")
    }


    private fun defineCycleTypes() {
        ModelMain.allCycleTypes.clear()
        ModelMain.allCycleTypeNames.clear()
        for (cycleType in CycleType.values()) {
            ModelMain.allCycleTypes.add(cycleType)
            ModelMain.allCycleTypeNames.add(getText(cycleType.rbKey))
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
            ModelMain.allAyanamshaNames.add(getText(ayanamsha.rbKey))
        }
    }

    private fun defineCoordinates() {
        ModelMain.allCoordinates.clear()
        ModelMain.allCoordinateNames.clear()
        for (coordinate in CycleCoordinateTypes.values()) {
            ModelMain.allCoordinates.add(coordinate)
            ModelMain.allCoordinateNames.add(getText(coordinate.rbKey))
        }
    }

    private fun setInitialValues() {
        setSelectedCycleType(CycleType.SINGLE_POINT)
    }

    fun setSelectedCycleType(cycleType: CycleType) {
        ModelMain.selectedCycleType = cycleType
    }


}