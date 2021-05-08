/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.exceptions.DateException
import io.kotest.matchers.ints.shouldBeExactly
import org.junit.jupiter.api.Assertions
import org.junit.Test

internal class DateTimeConverterTest {

    private val converter = DateTimeConverter()

    @Test
    fun `Happy flow for DateTimeConverter gives correct results`() {
        val dateTxt = "1953/1/29"
        val result = converter.dateElements(dateTxt)
        result.size shouldBeExactly 3
        result[0] shouldBeExactly 1953
        result[1] shouldBeExactly 1
        result[2] shouldBeExactly 29
    }

    @Test
    fun `Using too many parameters for DateTimeConverter throws a DateException`() {
        val dateTxt = "1953/1/29/8"
        val exception = Assertions.assertThrows(DateException::class.java) {
            converter.dateElements(dateTxt)
        }
    }

    @Test
    fun `Using invalid parameters for DateTimeConverter throws a DateException`() {
        val dateTxt = "1953/jan/29"
        val exception = Assertions.assertThrows(DateException::class.java) {
            converter.dateElements(dateTxt)
        }
    }

}