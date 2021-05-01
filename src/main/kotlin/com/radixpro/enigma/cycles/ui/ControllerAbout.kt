/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.libfe.fxbuilders.LabelBuilder
import com.radixpro.enigma.libfe.texts.Rosetta
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView

/**
 * Controller for ScreenAbout
 */
class ControllerAbout {

    val mainText = LabelBuilder().setText(Rosetta.getText("screenabout.maintext")).build()
    lateinit var imageView: ImageView

    fun init() {
        defineImage()
    }


    private fun defineImage(){
        val image = Image("img/ziggurat.png")
        imageView = ImageView(image)
        imageView.fitWidth = 223.0
        imageView.fitHeight = 86.0
        imageView.isPickOnBounds = true
        imageView.isPreserveRatio = true
    }
}