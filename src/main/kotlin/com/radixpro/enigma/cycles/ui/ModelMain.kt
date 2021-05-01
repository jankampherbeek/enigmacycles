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





}