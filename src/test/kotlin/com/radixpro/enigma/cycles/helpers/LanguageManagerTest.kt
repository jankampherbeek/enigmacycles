/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import com.radixpro.enigma.libfe.texts.Rosetta
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class LanguageManagerTest {

    @Test
    fun setItAndGetIt() {
        val lmgr = LanguageManager()
        lmgr.setCurrentLanguage("en")
        lmgr.getCurrentLanguage() shouldBe "en"
        lmgr.setCurrentLanguage("du")
        lmgr.getCurrentLanguage() shouldBe "du"
    }


}