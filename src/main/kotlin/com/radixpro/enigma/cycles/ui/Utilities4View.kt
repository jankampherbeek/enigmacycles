/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.ui

import com.radixpro.enigma.libfe.fxbuilders.LabelBuilder
import com.radixpro.enigma.libfe.fxbuilders.PaneBuilder
import javafx.scene.layout.Pane

/**
 * Util object for views.
 * TODO: move all to LibFE
 */
object Utilities4View {

    private const val STYLE_SUBTITLE_TEXT = "subtitletext"
    private const val STYLE_SUBTITLE_PANE = "subtitlepane"
    private const val STYLE_TITLE_TEXT = "titletext"
    private const val STYLE_TITLE_PANE = "titlepane"
    private const val SUBTITLE_HEIGHT = 30.0
    private const val TITLE_HEIGHT = 45.0

    fun createTitlePane(title: String, width: Double): Pane {
        return PaneBuilder()
            .setPrefWidth(width)
            .setPrefHeight(TITLE_HEIGHT)
            .setStyleClass(STYLE_TITLE_PANE)
            .setChildren(LabelBuilder()
                        .setText(title)
                        .setPrefWidth(width)
                        .setStyleClass(STYLE_TITLE_TEXT)
                        .build()
            ).build()
    }

    fun createSubTitlePane(subTitle: String, width: Double): Pane {
        return PaneBuilder()
            .setPrefWidth(width)
            .setPrefHeight(SUBTITLE_HEIGHT)
            .setStyleClass(STYLE_SUBTITLE_PANE)
            .setChildren(LabelBuilder()
                        .setText(subTitle)
                        .setPrefWidth(width)
                        .setStyleClass(STYLE_SUBTITLE_TEXT)
                        .build()
                ).build()
    }

}