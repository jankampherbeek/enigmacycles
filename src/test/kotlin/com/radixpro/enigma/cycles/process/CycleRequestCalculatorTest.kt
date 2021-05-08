/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.process

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.di.Injector
import com.radixpro.enigma.libfe.core.UiCelPoints
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.Test

internal class CycleRequestCalculatorTest {

    private val margin = 0.00000001
    private val processor = Injector.injectCycleRequestCalculator()

    @Test
    fun `Processing geocentric tropical singlePoint cycles in longitude should give correct results`() {
        val response = processor.calculateCycleRequest(createDefinition())
        response.size shouldBeExactly 2
        response[0].timePositions.size shouldBeExactly 10

        // todo check values
        response[0].timePositions[0].second shouldBe (280.00952758637 plusOrMinus margin)
        response[1].timePositions[0].second shouldBe (276.67035747650 plusOrMinus margin)      // Jupiter 2020/1/1 6d 40m 13s CP
        response[0].timePositions[3].second shouldBe (283.06766515666 plusOrMinus margin)      // Sun 2020/1/4  13d 4m 3s CP
        response[1].timePositions[3].second shouldBe (277.36118598819 plusOrMinus margin)      // Jupiter 2020/1/4 7d 21m 40s CP
        response[0].timePositions[5].first shouldBe (2458854.5 plusOrMinus margin)             // JdUt 2020/1/6 for Sun
        response[1].timePositions[5].first shouldBe (2458854.5 plusOrMinus margin)             // JdUt 2020/1/6 for Jupiter

    }


    private fun createDefinition(): CycleDefinition {
        val cycleCoordinates = CycleCoordinates(CycleCoordinateTypes.GEO_LONGITUDE, Zodiac.TROPICAL, UiAyanamsha.FAGAN)
        val celPoints = listOf(UiCelPoints.SUN, UiCelPoints.JUPITER)
        val period = CyclePeriod("2020/1/1", "2020/1/11", 1.0, true)
        return CycleDefinition(cycleCoordinates, CycleType.SINGLE_POINT, celPoints, period)
    }

    @Test
    fun tempTestForPeriod() {
        val cycleType = CycleType.SINGLE_POINT
        val gregorian = true
        val celPoints = listOf(UiCelPoints.OSCU_APOGEE)
        val period = CyclePeriod("16799/11/01", "16800/04/01", 1.0, gregorian)
//        val period = CyclePeriod("2998/01/01", "2999/01/01", 1.0, gregorian)
//        val period = CyclePeriod("2000/01/01", "2030/01/01", 100.0, gregorian)
//        val period = CyclePeriod("-13001/01/01", "-12997/01/01", 1.0, gregorian)
//        val period = CyclePeriod("-12999/01/01", "-12996/01/01", 1.0, gregorian)
        val coordinates = CycleCoordinates(CycleCoordinateTypes.GEO_LONGITUDE, Zodiac.TROPICAL, UiAyanamsha.NONE)
        val calculator = Injector.injectCycleRequestCalculator()
        val result = calculator.calculateCycleRequest(CycleDefinition(coordinates, cycleType, celPoints, period))
        for (timePos in result[0].timePositions) {
            print("" + timePos.second + ": " + timePos.first + ": ")
            printDateFromJd(timePos.first, gregorian)
        }
    }




    private fun printDateFromJd(jd: Double, gregorian: Boolean) {
        val workJd = jd + 0.5
        val intJd = workJd.toInt()
        val fractJd = workJd - intJd
        var calendarCorrectedJd = intJd
//        if (z >= 2291161) {   // Gregorian
        if (gregorian) {   // Gregorian            Meeus checks for z >= 2291161
            val alpha = ((intJd - 1867216.25) / 36524.25).toInt()
            calendarCorrectedJd = intJd + 1 + alpha - (alpha/4)
        }
        val b = calendarCorrectedJd + 1524
        val c = ((b - 122.1) /365.25).toInt()
        val d = (365.25 * c).toInt()
        val e = ((b - d)/30.6001).toInt()
        val day = b - d - (30.6001 * e).toInt() + fractJd
        val month =  if (e < 14) e - 1 else e - 13
        val year = if (month > 2) c - 4716 else c - 4715
        println("" + year + "/" + month + "/" + day.toInt())
    }


}