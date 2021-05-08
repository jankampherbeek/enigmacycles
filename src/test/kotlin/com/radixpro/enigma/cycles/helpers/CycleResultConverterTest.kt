/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.domain.CelPoints
import com.radixpro.enigma.libbe.domain.TimeSeriesValues
import com.radixpro.enigma.libfe.core.UiCelPoints
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class CycleResultConverterTest {

    private val margin = 0.00000001
    private val converter = CycleResultConverter(AstronApi())
    private lateinit var responseSingle: PresentableSingleCycleResult

    @BeforeEach
    fun setUp() {
        responseSingle = converter.responseSingleToPresResult(createDefinitionForSinglePoint(), createSingleTsValues())
    }

    @Test
    fun `Nr of dateTexts in reponseSingle should be correct`(){
        responseSingle.dateTexts.size shouldBeExactly 3
    }


    @Test
    fun `Nr of jdnrs in responseSingle should be correct`() {
        responseSingle.jdNrs.size shouldBeExactly 3
    }


    @Test
    fun `Nr of presentable values in responseSingle should be correct`(){
        responseSingle.presValues.size shouldBeExactly 2
    }


    @Test
    fun `Value for jdnr in responseSingle should be correct`(){
        responseSingle.jdNrs[1] shouldBe (1234568.0 plusOrMinus margin)
    }

    @Test
    fun `Value for dateTxt in responseSingle should be correct`() {
        responseSingle.dateTexts[2] shouldBe "-1332/01/13 12:00:00"
    }

    @Test
    fun `Indication of celestial point for a given range of values in responseSingle should be correct`(){
        responseSingle.presValues[0].point shouldBe UiCelPoints.SUN
    }

    @Test
    fun `Position of celestial point in responseSingle should be correct`() {
        responseSingle.presValues[0].positions[1] shouldBe (101.01 plusOrMinus margin)
    }


    private fun createDefinitionForSinglePoint(): CycleDefinition {
        val cycleCoordinates = CycleCoordinates(CycleCoordinateTypes.GEO_LONGITUDE, Zodiac.TROPICAL, UiAyanamsha.FAGAN)
        val cycletype = CycleType.SINGLE_POINT
        val celPoints = listOf(UiCelPoints.JUPITER, UiCelPoints.MARS)
        val cyclePeriod = CyclePeriod("2000/1/1", "2000/2/1", 1.0, true)
        return CycleDefinition(cycleCoordinates, cycletype, celPoints, cyclePeriod)
    }

    private fun createSingleTsValues(): List<TimeSeriesValues> {
        val listSun : MutableList<Pair<Double, Double>> = ArrayList()
        val listMoon : MutableList<Pair<Double, Double>> = ArrayList()
        listSun.add(Pair(1234567.0, 100.0))
        listSun.add(Pair(1234568.0, 101.01))
        listSun.add(Pair(1234569.0, 102.02))
        listMoon.add(Pair(1234567.0, 120.0))
        listMoon.add(Pair(1234568.0, 132.5))
        listMoon.add(Pair(1234569.0, 145.04))
        val tsValuesSun = TimeSeriesValues(CelPoints.SUN, listSun)
        val tsValuesMoon = TimeSeriesValues(CelPoints.MOON, listMoon)
        return listOf(tsValuesSun, tsValuesMoon)
    }


}