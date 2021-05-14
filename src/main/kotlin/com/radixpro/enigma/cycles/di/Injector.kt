/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.di

import com.radixpro.enigma.cycles.Cycles
import com.radixpro.enigma.cycles.helpers.*
import com.radixpro.enigma.cycles.process.CycleRequestCalculator
import com.radixpro.enigma.cycles.process.CycleRequestProcessor
import com.radixpro.enigma.cycles.ui.*
import com.radixpro.enigma.libbe.api.AstronApi
import com.radixpro.enigma.libfe.validation.Validator

object Injector {

    fun injectControllerMain(): ControllerMain {
        return ControllerMain(injectCycleRequestCalculator(), injectCycleResultConverter(), injectValidator(),
            injectDateTimeUtils())
    }

    fun injectCycleRequestCalculator(): CycleRequestCalculator {
        return CycleRequestCalculator(AstronApi(), injectDateTimeUtils())
    }

    fun injectCycleRequestProcessor(): CycleRequestProcessor {
        return CycleRequestProcessor(injectCycleRequestCalculator())
    }

    fun injectCycleResultConverter(): CycleResultConverter {
        return CycleResultConverter(AstronApi())
    }

    fun injectCycles(): Cycles {
        return Cycles(injectViewMain(), injectControllerMain())
    }

    private fun injectDateTimeConverter(): DateTimeConverter {
        return DateTimeConverter()
    }

    private fun injectDateTimeUtils(): DateTimeUtils {
        return DateTimeUtils(AstronApi(), injectDateTimeConverter())
    }

    fun injectDateValidator(): DateValidator {
        return DateValidator(AstronApi(), injectDateTimeConverter())
    }

    private fun injectLanguageManger(): LanguageManager {
        return LanguageManager()
    }

    private fun injectScreenAbout(): ScreenAbout {
        return ScreenAbout()
    }

    private fun injectScreenLineChart(): ScreenLineChart {
        return ScreenLineChart()
    }

    private fun injectScreenManual(): ScreenManual {
        return ScreenManual()
    }

    fun injectScreenSplash(): ScreenSplash {
        return ScreenSplash(injectCycles())
    }

    fun injectValidator(): Validator {
        return Validator()
    }

    fun injectViewMain(): ViewMain {
        return ViewMain(injectControllerMain())
    }

}