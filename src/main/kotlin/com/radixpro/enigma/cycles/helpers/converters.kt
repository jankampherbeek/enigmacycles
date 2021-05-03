/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.cycles.core.*
import com.radixpro.enigma.cycles.exceptions.DateException
import com.radixpro.enigma.cycles.ui.UiDictionary.DATE_SEPARATOR
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libbe.api.DateTimeTxtRequest
import com.radixpro.enigma.libbe.domain.TimeSeriesValues

class DateTimeConverter {

    fun dateElements(dateTxt: String): List<Int> {
        val errorTxt = "Error in date, parameters used: $dateTxt"
        val values = dateTxt.split(DATE_SEPARATOR)
        var day: Int
        var month: Int
        var year: Int
        if (values.size == 3) {
            try {
                year = values[0].toInt()
                month = values[1].toInt()
                day = values[2].toInt()
            } catch (e: Exception) {
                throw DateException(errorTxt)
            }
        } else throw DateException(errorTxt)
        return listOf(year, month, day)

    }
}

class CycleResultConverter(private val astronApi: AstronApi) {

    fun responseSingleToPresResult(definition: CycleDefinition,
                                   values: List<TimeSeriesValues>): PresentableSingleCycleResult {
        val jdNrs = createJdNrs(values[0])
        val dateTxts = createDateTxts(jdNrs, definition.cyclePeriod.gregorian)
        val presValues = createSinglePresValues(values)
        return PresentableSingleCycleResult(definition, jdNrs, dateTxts, presValues)
    }

//    fun responseSummedToPresResult(definition: CycleDefinition,
//                                   values: List<TimeSeriesValues>): PresentableSummedCycleResult {
//        val jdNrs = createJdNrs(values[0])
//        val dateTxts = createDateTxts(jdNrs, definition.cyclePeriod.gregorian)
//        val presSummedValues = createSummedPresValues(definition, values)
//        return PresentableSummedCycleResult(definition, jdNrs, dateTxts, presSummedValues[0], presSummedValues[1])
//    }

    private fun createJdNrs(tsValues: TimeSeriesValues): List<Double> {
        val jdNrs = mutableListOf<Double>()
        for (valuePair in tsValues.timePositions) jdNrs.add(valuePair.first)
        return jdNrs
    }

    private fun createDateTxts(jdNrs: List<Double>, gregorian: Boolean): List<String> {
        val dateTxts = mutableListOf<String>()
        for (jdNr in jdNrs) {
            val request = DateTimeTxtRequest(jdNr, gregorian)
            val response = astronApi.constructDateTimeFromJd(request)
            if (!response.errors) dateTxts.add(response.result)
            else dateTxts.add("error!")
        }
        return dateTxts
    }

    private fun createSinglePresValues(allTsValues: List<TimeSeriesValues>): List<PresentableTSValues> {
        val presentableTSValues = mutableListOf<PresentableTSValues>()
        val nrOfPoints = allTsValues.size
        for (i in 0 until nrOfPoints) {
            val positionsForPoint = mutableListOf<Double>()
            val listForPoint = allTsValues[i]
            for (value in listForPoint.timePositions) positionsForPoint.add(value.second)
            val uiCelPoint = UiCelPoints.valueOf(listForPoint.celPoint.name)
            presentableTSValues.add(PresentableTSValues(uiCelPoint, positionsForPoint))
        }
        return presentableTSValues
    }

//    private fun createSummedPresValues(definition: CycleDefinition,
//                                       allTsValues: List<TimeSeriesValues>): List<List<PresentableTSValues>> {
//        val presentableAddValues = mutableListOf<PresentableTSValues>()
//        val prentableSubtractValues = mutableListOf<PresentableTSValues>()
//        val nrOfPoints = allTsValues.size
//        var summCPIds = mutableListOf<Int>()
//        for (i in 0 until definition.summableCelPoint.size) {
//            summCPIds.add(definition.summableCelPoint[i].celPoint.seId)
//        }
//        for (i in 0 until nrOfPoints) {
//            val positionsForPoint = mutableListOf<Double>()
//            val listForPoint = allTsValues[i]
//            for (value in listForPoint.timePositions) positionsForPoint.add(value.second)
//            val uiCelPoint = UiCelPoints.valueOf(listForPoint.celPoint.name)
//            if (uiCelPoint.seId in summCPIds) {
//                for (summableCP in definition.summableCelPoint) {
//                    if (summableCP.celPoint.seId == uiCelPoint.seId) {
//                        if (summableCP.positive)
//                            presentableAddValues.add(PresentableTSValues(uiCelPoint, positionsForPoint))
//                        else
//                            prentableSubtractValues.add(PresentableTSValues(uiCelPoint, positionsForPoint))
//                    }
//                }
//            }
//        }
//        return listOf(presentableAddValues, prentableSubtractValues)
//    }

}
