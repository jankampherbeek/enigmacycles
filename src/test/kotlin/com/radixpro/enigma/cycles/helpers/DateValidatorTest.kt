/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.di.Injector
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DateValidatorTest {

    private val validator = Injector.injectDateValidator()

    @Test
    fun `Happy flow with correct date for DateValidator returns valid`() {
        val dateTxt = "2021/4/7"
        validator.checkDate(dateTxt, true) shouldBe true
    }

    @Test
    fun `DateValidator returns not valid when using impossible date`() {
        val dateTxt = "2021/13/7"
        validator.checkDate(dateTxt, true) shouldBe false
    }

    @Test
    fun `DateValidator returns not valid when using incorrectly formatted date`() {
        val dateTxt = "2021-4-7"
        validator.checkDate(dateTxt, true) shouldBe false
    }

    @Test
    fun `DateValidator returns not valid when using non existing leapday`() {
        val dateTxt = "2021-2-29"
        validator.checkDate(dateTxt, true) shouldBe false
    }

    @Test
    fun `DateValidator returns valid when using existing leapday`() {
        val dateTxt = "2020/2/29"
        validator.checkDate(dateTxt, true) shouldBe true
    }

    @Test
    fun `DateValidator returns valid when using leapyear that is a multiple of 400 using gregorian calendar`() {
        val dateTxt = "1600/2/29"
        validator.checkDate(dateTxt, true) shouldBe true
    }

    // FIXME check handling of this test in libBE
//    @Test
//    fun `DateValidator returns invalid when using leapyear that is a multiple of 400 using julian calendar`() {
//        val dateTxt = "1600/2/29"
//        validator.checkDate(dateTxt, false) shouldBe false
//    }

}