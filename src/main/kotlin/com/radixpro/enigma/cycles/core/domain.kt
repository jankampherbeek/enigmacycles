/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.core

data class SummableCelPoint(val celPoint: UiCelPoints, val positive: Boolean)

data class CycleCoordinates(val coordinateSystem: CoordinateSystem,
                            val eclipticCoordinates: EclipticCoordinates,
                            val equatorialCoordinates: EquatorialCoordinates,
                            val zodiac: Zodiac,
                            val ayanamsha: UiAyanamsha)

data class CyclePeriod(val startDateTxt: String, val endDateTxt: String, val interval: Int)

data class CycleSettings(val cycleCoordinates: CycleCoordinates,
                         val center: Center,
                         val cycleType: CycleType,
                         val celPoints: List<UiCelPoints>,
                         val summableCelPoint: List<SummableCelPoint>,
                         val CyclePeriod: CyclePeriod)