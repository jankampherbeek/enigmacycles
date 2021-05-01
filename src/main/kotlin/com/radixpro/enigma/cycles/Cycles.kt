/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles

import com.radixpro.enigma.cycles.ui.ControllerMain
import com.radixpro.enigma.cycles.ui.ModelMain
import com.radixpro.enigma.cycles.ui.ScreenSplash
import com.radixpro.enigma.cycles.ui.ViewMain
import javafx.scene.Scene
import javafx.stage.Stage


/**
 * Main application.
 */
class Cycles(private val viewMain: ViewMain,
             private val controllerMain: ControllerMain) {

    val stage = Stage()

    fun startCycles() {

//        val parentView = viewMain.asParent()
//        val scene = Scene(parentView, viewMain.height, viewMain.width)
//        stage.scene = scene
//        stage.show()
        viewMain.show()
    }

}