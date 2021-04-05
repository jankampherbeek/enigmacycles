/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.helpers

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Handles i18N
 */
class LanguageManager {

    fun getCurrentLanguage(): String {
        val properties = Properties()
        val fis = FileInputStream("data" + File.separator +  "settings.properties")
        properties.load(fis)
        return properties.getProperty("language")
    }

    fun setCurrentLanguage(lang: String) {
        val properties = Properties()
        properties.setProperty("language", lang)
        val fos = FileOutputStream("data" + File.separator +  "settings.properties")
        properties.store(fos, "new lang")
    }



}