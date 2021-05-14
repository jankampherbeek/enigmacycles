/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.process

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.helpers.DateTimeUtils
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.TimeSeriesRequest
import com.radixpro.enigma.libbe.domain.*

class CycleRequestProcessor(private val calculator: CycleRequestCalculator) {

    fun processCycleRequest(definition: CycleDefinition) {
        val calculatedTimeSeries = calculator.calculateCycleRequest(definition)
        if (definition.cycleType == CycleType.SINGLE_POINT) {

        } else if (definition.cycleType == CycleType.WAVES) {

        }

    }
}

/**
 * Calculates positions as a TimeSeries for several types of cycles.
 */
class CycleRequestCalculator(private val astronApi: AstronApi, private val dateTimeUtils: DateTimeUtils) {

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
        val jdStart = dateTimeUtils.defineJdUt(definition.cyclePeriod.startDateTxt, definition.cyclePeriod.gregorian)
        val jdEnd = dateTimeUtils.defineJdUt(definition.cyclePeriod.endDateTxt, definition.cyclePeriod.gregorian)
        val nrOfDays = jdEnd - jdStart
        val nrOfEvents = nrOfDays / interval
        return TimeSeriesRequest(celPoints, observerPos, coordinateType, jdStart, location, interval, nrOfEvents.toInt())


    }

}