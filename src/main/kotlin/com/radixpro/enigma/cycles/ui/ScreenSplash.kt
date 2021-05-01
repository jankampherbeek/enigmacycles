/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.cycles.Cycles
import javafx.animation.FadeTransition
import javafx.beans.value.ObservableValue
import javafx.concurrent.Task
import javafx.concurrent.Worker
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration

/**
 * Splash screen that shows itself during 1 second after starting the application.<br/>
 * Code based on TaskBasedSplash.java by JewelSea: https://gist.github.com/jewelsea/2305098
 */
class ScreenSplash(private val cycles: Cycles) {

    private val height = 500.0
    private val width = 500.0
    private val splashImage = "img/cycle-img.jpg"
    private lateinit var splashLayout: Pane
    private var mainStage = Stage()

    fun show() {
        init()
        start(mainStage)
    }

    private fun init() {
        splashLayout = VBox()
        splashLayout.children.addAll(definePane())
    }

    @Throws(Exception::class)
    fun start(initStage: Stage) {
        val dummyTask: Task<Any> = object : Task<Any>() {
            @Throws(InterruptedException::class)
            override fun call() {
                Thread.sleep(1000)
            }
        }
        showSplash(initStage, dummyTask, object : InitCompletionHandler {
            override fun complete() {
                startMainProgram()
            }
        })
        Thread(dummyTask).start()
    }

    private fun startMainProgram() {
        cycles.startCycles()
    }

    private fun showSplash(
        initStage: Stage,
        task: Task<*>,
        initCompletionHandler: InitCompletionHandler) {
        task.stateProperty()
            .addListener { _: ObservableValue<out Worker.State>?, _: Worker.State?, newState: Worker.State ->
                if (newState == Worker.State.SUCCEEDED) {
                    initStage.toFront()
                    val fadeSplash = FadeTransition(Duration.seconds(1.2), splashLayout)
                    fadeSplash.fromValue = 1.0
                    fadeSplash.toValue = 0.0
                    fadeSplash.onFinished = EventHandler { initStage.hide() }
                    fadeSplash.play()
                    initCompletionHandler.complete()
                }
            }
        val splashScene = Scene(splashLayout, Color.TRANSPARENT)
        initStage.scene = splashScene
        initStage.initStyle(StageStyle.TRANSPARENT)
        initStage.isAlwaysOnTop = true
        initStage.show()
    }

    interface InitCompletionHandler {
        fun complete()
    }

    private fun definePane(): Pane {
        val lblEnigma = Label("Enigma")
        lblEnigma.font = Font.font("Verdana", FontWeight.SEMI_BOLD, 64.0)
        lblEnigma.textFill = Color.STEELBLUE
        lblEnigma.prefHeight = height
        lblEnigma.prefWidth = width
        lblEnigma.alignment = Pos.TOP_CENTER
        val lblCycles = Label("Cycles 1.0")
        lblCycles.font = Font.font("Verdana", FontWeight.SEMI_BOLD, 48.0)
        lblCycles.textFill = Color.LIGHTSTEELBLUE
        lblCycles.prefHeight = height
        lblCycles.prefWidth = width
        lblCycles.alignment = Pos.BOTTOM_CENTER

        val pane = Pane()
        pane.children.addAll(createImage(), lblEnigma, lblCycles)
        return pane
    }

    private fun createImage(): ImageView {
        val image = Image(splashImage)
        val imageView = ImageView(image)
        imageView.fitWidth = width
        imageView.fitHeight = height
        imageView.isPickOnBounds = true
        imageView.isPreserveRatio = true
        return imageView
    }

}