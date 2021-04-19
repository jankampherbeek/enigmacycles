/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.cycles.core.PresentableSingleCycleResult
import com.radixpro.enigma.cycles.ui.UiDictionary.GAP
import com.radixpro.enigma.libfe.fxbuilders.GridPaneBuilder
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class ScreenLineChart {

    private val stage = Stage()
    private val width = 1000.0
    private val height = 800.0
    private lateinit var presResult: PresentableSingleCycleResult

    fun show(presResult: PresentableSingleCycleResult) {   // TODO hier interface boven zetten en ook PresentableSummedCycleResult mogelijk maken
        this.presResult = presResult
        initialize()
    }

    private fun initialize() {
        stage.scene = Scene(defineGridPane())
        stage.show()
    }

    private fun defineGridPane(): GridPane {
        val grid = GridPaneBuilder().setPadding(Insets(GAP)).setPrefWidth(width).setPrefHeight(height).build()
        grid.add(createLineChart(), 0, 0, 1, 1)
        return grid
    }

    private fun createLineChart(): LineChart<Number, Number> {
        val xyPositions = defineXYPositions()
        val xAxis = NumberAxis()
        xAxis.label = "Time"
        val yAxis = NumberAxis()
        yAxis.label = "Longitude"
        val lineChart = LineChart<Number, Number>(xAxis, yAxis)
        lineChart.prefWidth = 950.0
        lineChart.prefHeight = 700.0
        lineChart.title = "Temporary title"
        lineChart.createSymbols = false
        val data = XYChart.Series<Number, Number>()
        data.name = "Planet"
        var xPos = 0
        for (xyPos in xyPositions) {
            data.data.add(XYChart.Data(xPos, xyPos))
            xPos+= 10
        }
        lineChart.data.add(data)
        return lineChart
    }

    private fun defineXYPositions(): List<Double> {
        val xyPositions = mutableListOf<Double>()
        for (pv in presResult.presValues) {
            for (pos in pv.positions) {
                xyPositions.add(pos)
            }
        }
        return xyPositions
    }

}