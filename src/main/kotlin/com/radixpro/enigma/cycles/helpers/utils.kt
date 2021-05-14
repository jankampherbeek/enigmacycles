/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.exceptions.DateException
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.JdUtRequest
import com.radixpro.enigma.libbe.domain.DateTimeParts

class DateTimeUtils(private val astronApi: AstronApi, private val dateTimeConverter: DateTimeConverter) {

    @Throws(DateException::class)
    fun defineJdUt(dateTxt: String, gregorian: Boolean): Double {
        val dmy =dateTimeConverter.dateElements(dateTxt)
        val dateTimeParts = DateTimeParts(dmy[0], dmy[1], dmy[2], 0,0,0,0.0, gregorian)
        val jdUtRequest = JdUtRequest(dateTimeParts)
        val response = astronApi.calcJdUt(jdUtRequest)
        if (response.errors) {
            // todo log exception
            throw DateException("Exception when calculating jd for " + dateTxt + " and gregorian is " + gregorian
                    + ". Original message : " + response.comments)
        }
        return astronApi.calcJdUt(jdUtRequest).result
    }


}