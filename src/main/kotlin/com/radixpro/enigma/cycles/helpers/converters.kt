/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.exceptions.DateException
import com.radixpro.enigma.cycles.ui.UiDictionary.DATE_SEPARATOR

class DateTimeConverter() {

    fun dateElements(dateTxt: String): List<Int> {
        val values = dateTxt.split(DATE_SEPARATOR)
        var day: Int = 0
        var month: Int = 0
        var year: Int = 0
        if (values.size == 3) {
            try {
                year = values[0].toInt()
                month = values[1].toInt()
                day = values[2].toInt()
            } catch (e: Exception) {
                throw DateException("Error in date, parametes used: $dateTxt")
            }
        } else throw DateException("Error in date, parametes used: $dateTxt")
        return listOf(year, month, day)

    }

}
