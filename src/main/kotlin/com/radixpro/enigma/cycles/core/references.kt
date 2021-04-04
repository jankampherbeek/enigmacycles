/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.core

import com.radixpro.enigma.libbe.domain.CelPointCategory

enum class Center(val rbKey: String) {
    GEOCENTRIC("enum.center.geocentric"),
    HELIOCENTRIC("enum.center.heliocentric")
}

enum class CycleType(val rbKey: String) {
    SINGLE_POINT("enum.cycletype.singlepoint"),
    SUM_OF_POINTS("enum.cycletype.sumofpoints")
}

enum class CycleCoordinateTypes(val rbKey: String) {
    LONGITUDE("enum.eclipticcoordinates.longitude"),
    LATITUDE("enum.eclipticcoordinates.latitude"),
    RIGHT_ASCENSION("enum.equatorialcoordinates.ra"),
    DECLINATION("enum.equatorialcoordinates.declination")
}

enum class UiAyanamsha(val seId: Int, val rbKey: String) {
    FAGAN(0, "enum.uiayanamsha.fagan"),
    LAHIRI(1, "enum.uiayanamsha.lahiri"),
    RAMAN(3, "enum.uiayanamsha.raman"),
    KRISHNAMURTI(5, "enum.uiayanamsha.krishnamurti"),
    HUBER(12, "enum.uiayanamsha.huber"),
    GALACTIC_CTR_BRAND(30, "enum.uiayanamsha.brand")
}

enum class UiCelPoints(val seId: Int, val rbKey: String) {
    SUN(0, "enum.uicelpoints.sun"),
    MOON(1, "enum.uicelpoints.moon"),
    MERCURY(2, "enum.uicelpoints.mercury"),
    VENUS(3, "enum.uicelpoints.venus"),
    EARTH(14, "enum.uicelpoints.earth"),
    MARS(4, "enum.uicelpoints.mars"),
    JUPITER(5, "enum.uicelpoints.jupiter"),
    SATURN(6, "enum.uicelpoints.saturn"),
    URANUS(7, "enum.uicelpoints.uranus"),
    NEPTUNE(8, "enum.uicelpoints.neptune"),
    PLUTO(9, "enum.uicelpoints.pluto"),
    MEAN_NODE(10, "enum.uicelpoints.meannode"),
    TRUE_NODE(11, "enum.uicelpoints.truenode"),
    MEAN_APOGEE(12, "enum.uicelpoints.meanapogee"),
    OSCU_APOGEE(13, "enum.uicelpoints.oscuapogee"),
    CHEIRON(15, "enum.uicelpoints.cheiron"),
    PHOLUS(16, "enum.uicelpoints.pholus"),
    NESSUS(17066, "enum.uicelpoints.nessus"),
    CERES(17, "enum.uicelpoints.ceres"),
    PALLAS(18, "enum.uicelpoints.pallas"),
    JUNO(19, "enum.uicelpoints.juno"),
    VESTA(20, "enum.uicelpoints.vesta"),
    HUYA(48628, "enum.uicelpoints.huya"),
    MAKEMAKE(146472, "enum.uicelpoints.makemake"),
    HAUMEA( 146108, "enum.uicelpoints.haumea"),
    ERIS( 146199, "enum.uicelpoints.eris"),
    IXION( 38978, "enum.uicelpoints.ixion"),
    ORCUS( 100482, "enum.uicelpoints.orcus"),
    QUAOAR( 60000, "enum.uicelpoints.quaoar"),
    SEDNA( 100377, "enum.uicelpoints.sedna"),
    VARUNA(30000, "enum.uicelpoints.varuna"),
}

enum class Zodiac(val rbKey: String) {
    TROPICAL("enum.zodiac.tropical"),
    SIDEREAL("enum.zodiac.sidereal")
}


