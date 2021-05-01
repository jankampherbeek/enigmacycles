/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.process

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.exceptions.DateException
import com.radixpro.enigma.cycles.helpers.DateTimeConverter
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.JdUtRequest
import com.radixpro.enigma.libbe.api.TimeSeriesRequest
import com.radixpro.enigma.libbe.domain.*

class CycleRequestProcessor(private val calculator: CycleRequestCalculator) {

    fun processCycleRequest(definition: CycleDefinition) {
        val calculatedTimeSeries = calculator.calculateCycleRequest(definition)
        if (definition.cycleType == CycleType.SINGLE_POINT) {

        } else if (definition.cycleType == CycleType.SUM_OF_POINTS) {

        }

    }


}





/**
 * Calculates positions as a TimeSeries for several types of cycles.
 */
class CycleRequestCalculator(private val astronApi: AstronApi, private val dateTimeConverter: DateTimeConverter) {

    fun calculateCycleRequest(definition: CycleDefinition): List<TimeSeriesValues> {
        val request = createRequest(definition)
        val response = astronApi.calcTimeSeries(request)
        if (response.errors) {
            // todo log error
            // throw exception
        }
        return response.result
    }

    // todo handle exceptions
    private fun createRequest(definition: CycleDefinition): TimeSeriesRequest {
        val celPoints = mutableListOf<CelPoints>()
        for (point in definition.celPoints) {
            celPoints.add(CelPoints.valueOf(point.name))
        }
        val observerPos = ObserverPos.GEOCENTRIC   // FIXME, define observerpos
        val coordinateType = if (definition.cycleCoordinates.cycleCoordinateType == CycleCoordinateTypes.GEO_LATITUDE
            || definition.cycleCoordinates.cycleCoordinateType == CycleCoordinateTypes.GEO_LONGITUDE) CoordinateTypes.ECLIPTICAL
        else CoordinateTypes.EQUATORIAL
        val location = Location(0.0, 0.0)
        val interval = definition.cyclePeriod.interval
        val jdStart = defineJdUt(definition.cyclePeriod.startDateTxt, definition.cyclePeriod.gregorian)
        val jdEnd = defineJdUt(definition.cyclePeriod.endDateTxt, definition.cyclePeriod.gregorian)
        val nrOfDays = jdEnd - jdStart
        val nrOfEvents = nrOfDays / interval
        return TimeSeriesRequest(celPoints, observerPos, coordinateType, jdStart, location, interval, nrOfEvents.toInt())


    }

    @Throws(DateException::class)
    private fun defineJdUt(dateTxt: String, gregorian: Boolean): Double {
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