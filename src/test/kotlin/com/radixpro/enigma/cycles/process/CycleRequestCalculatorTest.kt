/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.process

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.di.Injector
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CycleRequestCalculatorTest {

    private val margin = 0.00000001
    private val processor = Injector.injectCycleRequestCalculator()

    @Test
    fun `Processing geocentric tropical singlePoint cycles in longitude should give correct results`() {
        val response = processor.calculateCycleRequest(createDefinition())
        response.size shouldBeExactly 2
        response[0].timePositions.size shouldBeExactly 10

        // todo check values
        response[0].timePositions[0].second shouldBe (280.00953615467 plusOrMinus margin)      // Sun 2020/1/1 10d 0m 34s CP
        response[1].timePositions[0].second shouldBe (276.67034628232 plusOrMinus margin)      // Jupiter 2020/1/1 6d 40m 13s CP
        response[0].timePositions[3].second shouldBe (283.06767338285 plusOrMinus margin)      // Sun 2020/1/4  13d 4m 3s CP
        response[1].timePositions[3].second shouldBe (277.36117523523 plusOrMinus margin)      // Jupiter 2020/1/4 7d 21m 40s CP
        response[0].timePositions[5].first shouldBe (2458854.5 plusOrMinus margin)             // JdUt 2020/1/6 for Sun
        response[1].timePositions[5].first shouldBe (2458854.5 plusOrMinus margin)             // JdUt 2020/1/6 for Jupiter

    }


    private fun createDefinition(): CycleDefinition {
        val cycleCoordinates = CycleCoordinates(CycleCoordinateTypes.LONGITUDE, Zodiac.TROPICAL, UiAyanamsha.FAGAN)
        val celPoints = listOf(UiCelPoints.SUN, UiCelPoints.JUPITER)
        val summableCelPoints = ArrayList<SummableCelPoint>()
        val period = CyclePeriod("2020/1/1", "2020/1/11", 1.0, true)
        return CycleDefinition(cycleCoordinates, Center.GEOCENTRIC, CycleType.SINGLE_POINT, celPoints, summableCelPoints, period)
    }

}