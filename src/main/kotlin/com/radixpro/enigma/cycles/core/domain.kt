/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.core

import com.radixpro.enigma.libbe.domain.ObserverPos

data class SummableCelPoint(val celPoint: UiCelPoints, val positive: Boolean)

data class CycleCoordinates(val cycleCoordinateType: CycleCoordinateTypes,
                            val zodiac: Zodiac,
                            val ayanamsha: UiAyanamsha)

data class CyclePeriod(val startDateTxt: String, val endDateTxt: String, val interval: Double, val gregorian: Boolean)

data class CycleDefinition(val cycleCoordinates: CycleCoordinates,
                           val cycleType: CycleType,
                           val celPoints: List<UiCelPoints>,
                           val cyclePeriod: CyclePeriod)

data class PresentableTSValues(val point: UiCelPoints, val positions: List<Double>)

data class PresentableSingleCycleResult(val definition: CycleDefinition,
                                        val jdNrs: List<Double>,
                                        val dateTexts: List<String>,
                                        val presValues: List<PresentableTSValues>)

data class PresentableSummedCycleResult(val definition: CycleDefinition,
                                        val jdNrs: List<Double>,
                                        val dateTexts: List<String>,
                                        val presValuesAdd: List<PresentableTSValues>,
                                        val presValuesSubtract: List<PresentableTSValues>)

data class CpSelectModus(val observerPos: ObserverPos,
                         val categories: List<CelPointCat>,
                         val checkForPeriod: Boolean,
                         val jdStart: Double,
                         val jdEnd: Double)