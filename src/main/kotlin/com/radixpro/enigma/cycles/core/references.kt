/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.core

// TODO move shareable enums to libFE
enum class CycleType(val rbKey: String) {
    SINGLE_POINT("enum.cycletype.singlepoint"),
    WAVES("enum.cycletype.waves")
}

enum class CycleCoordinateTypes(val rbKey: String) {
    GEO_LONGITUDE("enum.eclipticcoordinates.geolong"),
    HELIO_LONGITUDE("enum.eclipticcoordinates.heliolong"),
    GEO_LATITUDE("enum.eclipticcoordinates.geolat"),
    HELIO_LATITUDE("enum.eclipticcoordinates.heliolat"),
    RIGHT_ASCENSION("enum.equatorialcoordinates.ra"),
    DECLINATION("enum.equatorialcoordinates.declination")
}

enum class UiAyanamsha(val seId: Int, val rbKey: String) {
    NONE(-1, "enum.uiayanamsha.none"),
    FAGAN(0, "enum.uiayanamsha.fagan"),
    LAHIRI(1, "enum.uiayanamsha.lahiri"),
    RAMAN(3, "enum.uiayanamsha.raman"),
    KRISHNAMURTI(5, "enum.uiayanamsha.krishnamurti"),
    HUBER(12, "enum.uiayanamsha.huber"),
    GALACTIC_CTR_BRAND(30, "enum.uiayanamsha.brand")
}

enum class Zodiac(val rbKey: String) {
    TROPICAL("enum.zodiac.tropical"),
    SIDEREAL("enum.zodiac.sidereal")
}


