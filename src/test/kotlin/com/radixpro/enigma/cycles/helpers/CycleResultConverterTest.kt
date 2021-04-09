/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.di.Injector
import com.radixpro.enigma.libbe.domain.CelPoints
import com.radixpro.enigma.libbe.domain.ObserverPos
import com.radixpro.enigma.libbe.domain.TimeSeriesValues
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.sql.Time

internal class CycleResultConverterTest {

    private val margin = 0.00000001
    private val converter = CycleResultConverter(AstronApi())

    @Test
    fun `Converting resonse for singlepoint cycles to a presentable version should give the correct result`() {
        val definition = createDefinitonForSinglePoint()
        val tsValues = createTsValues()
        val response = converter.responseToPresResult(definition, tsValues)
        response.dateTexts.size shouldBeExactly 3
        response.jdNrs.size shouldBeExactly 3
        response.presValues.size shouldBeExactly 2
        response.jdNrs[1] shouldBe (1234568.0 plusOrMinus margin)
        response.dateTexts[2] shouldBe "-1332/01/13 12:00:00"
        response.presValues[0].point shouldBe UiCelPoints.SUN
        response.presValues[0].positions[1] shouldBe (101.01 plusOrMinus margin)
    }

    private fun createDefinitonForSinglePoint(): CycleDefinition {
        val cycleCoordinates = CycleCoordinates(CycleCoordinateTypes.LONGITUDE, Zodiac.TROPICAL, UiAyanamsha.FAGAN)
        val center = Center.GEOCENTRIC
        val cycletype = CycleType.SINGLE_POINT
        val celPoints = listOf(UiCelPoints.JUPITER, UiCelPoints.MARS)
        val summableCelPoint = ArrayList<SummableCelPoint>()
        val cyclePeriod = CyclePeriod("2000/1/1", "2000/2/1", 1.0, true)
        return CycleDefinition(cycleCoordinates, center, cycletype, celPoints, summableCelPoint, cyclePeriod)
    }

    private fun createTsValues(): List<TimeSeriesValues> {
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