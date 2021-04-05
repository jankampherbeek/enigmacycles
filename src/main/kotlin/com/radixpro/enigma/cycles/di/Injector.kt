/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.di

import com.radixpro.enigma.cycles.helpers.DateValidator
import com.radixpro.enigma.cycles.helpers.LanguageManager
import com.radixpro.enigma.cycles.ui.ScreenAbout
import com.radixpro.enigma.cycles.ui.ScreenInput
import com.radixpro.enigma.cycles.ui.ScreenManual
import com.radixpro.enigma.cycles.ui.ScreenStart
import com.radixpro.enigma.libbe.api.AstronApi

object Injector {

    private fun injectDateValidator(): DateValidator {
        return DateValidator(AstronApi())
    }

    private fun injectLanguageManger(): LanguageManager {
        return LanguageManager()
    }

    private fun injectScreenAbout(): ScreenAbout {
        return ScreenAbout()
    }

    private fun injectScreenInput(): ScreenInput {
        return ScreenInput(injectDateValidator())
    }

    private fun injectScreenManual(): ScreenManual {
        return ScreenManual()
    }

    fun injectScreenStart(): ScreenStart {
        return ScreenStart(injectScreenInput(), injectScreenAbout(), injectScreenManual(), injectLanguageManger())
    }

}