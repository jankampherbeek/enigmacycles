/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.di

import com.radixpro.enigma.cycles.helpers.DateValidator
import com.radixpro.enigma.cycles.ui.ScreenInput
import com.radixpro.enigma.libbe.api.AstronApi

object Injector {

    private fun injectDateValidator(): DateValidator {
        return DateValidator(AstronApi())
    }

    fun injectScreenInput(): ScreenInput {
        return ScreenInput(injectDateValidator())
    }

}