/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.ValidDateRequest

class DateValidator(private val api: AstronApi, private val converter: DateTimeConverter) {

    fun checkDate(inputText: String, greg: Boolean): Boolean {
        return try {
            val values = converter.dateElements(inputText)
            val request = ValidDateRequest(values[0], values[1], values[2], greg)
            api.isValidDate(request)
        } catch (e: Exception) {
            false
        }
    }

}