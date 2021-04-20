/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import javafx.scene.chart.XYChart
import javafx.scene.chart.LineChart
import javafx.scene.Scene
import javafx.scene.chart.NumberAxis
import javafx.scene.layout.HBox
import javafx.stage.Stage
//import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO

import javafx.scene.image.WritableImage
import java.io.File


class TestGraphics {

    fun testIt() {
        val stage = Stage()
        initUI(stage)
    }

    private fun initUI(stage: Stage) {
        val root = HBox()
        val scene = Scene(root, 450.0, 330.0)
        val xAxis = NumberAxis()
        xAxis.setLabel("Age")
        val yAxis = NumberAxis()
        yAxis.setLabel("Salary (€)")
        val lineChart = LineChart<Number, Number>(xAxis, yAxis)
        lineChart.title = "Average salary per age"
        lineChart.createSymbols = false
        val data = XYChart.Series<Number, Number>()
        data.name = "2016"
        data.data.add(XYChart.Data(18, 567))
        data.data.add(XYChart.Data(20, 612))
        data.data.add(XYChart.Data(25, 800))
        data.data.add(XYChart.Data(30, 980))
        data.data.add(XYChart.Data(40, 1410))
        data.data.add(XYChart.Data(50, 2350))
        lineChart.data.add(data)

        val data2 = XYChart.Series<Number, Number>()
        data2.name = "2017"
        data2.data.add(XYChart.Data(18, 367))
        data2.data.add(XYChart.Data(20, 212))
        data2.data.add(XYChart.Data(25, 900))
        data2.data.add(XYChart.Data(30, 780))
        data2.data.add(XYChart.Data(40, 410))
        data2.data.add(XYChart.Data(50, 350))
        lineChart.data.add(data2)


        root.children.add(lineChart)

        createImage(scene)

        stage.setTitle("LineChart")
        stage.setScene(scene)
        stage.show()
    }


    private fun createImage(scene: Scene) {
        //Saving the scene as image
        //Saving the scene as image
        val image: WritableImage = scene.snapshot(null)
        val file = File("D:\\tempChart.png")
//        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", file)
        println("Image Saved")
    }


}