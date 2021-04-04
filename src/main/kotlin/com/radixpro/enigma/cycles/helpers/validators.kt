/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.ui.UiDictionary.DATE_SEPARATOR
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.ValidDateRequest

class DateValidator(private val api: AstronApi) {

    fun checkDate(inputText: String, greg: Boolean): Boolean {
        var validated = false
        val day: Int
        val month: Int
        val year: Int
        val values = inputText.split(DATE_SEPARATOR)
        if (values.size == 3) {
            try {
                year = values[0].toInt()
                month = values[1].toInt()
                day = values[2].toInt()
                val request = ValidDateRequest(year, month, day, greg)
                validated = api.isValidDate(request)
            } catch (nfe: NumberFormatException) {
                validated = false
            }
        }
        return validated
    }

}