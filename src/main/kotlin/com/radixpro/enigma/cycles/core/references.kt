/*
 * Jan Kampherbeek, (c)  2021.
 * Enigma Cycles is open source.
 * Please check the file copyright.txt in the root of the source for further details
 */

package com.radixpro.enigma.cycles.core

// TODO move shareable enums to libFE
enum class CycleType(val rbKey: String) {
    SINGLE_POINT("enum.cycletype.singlepoint"),
    SUM_OF_POINTS("enum.cycletype.sumofpoints")
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

/**
 * Categories for celestial points.
 * @param rbKey Key for Resource Bundle.
 */
enum class CelPointCat(val rbKey: String) {
    CLASSIC("lshr.cpcat.classic"),
    MODERN("lshr.cpcat.modern"),
    PLUTOID("lshr.cpcat.plutoid"),
    ASTEROID("lshr.cpcat.asteroid"),
    MATH_POINT("lshr.cpcat.mathpoint"),
    CENTAUR("lshr.cpcat.centaur")
}

/**
 * Celestial points.
 * @param seId id as used by the Swiss Ephejmeris
 * @param category Category for celestial points
 * @param glyph String to present celestial symbol using font EnigmaAstrology.
 * @param rbKey Key for Resource Bundle.
 *
 *
 * Versions since 2.00, which are based on JPL Ephemeris DE431, work for the following time range:

Start date         11 Aug 13000 BCE (-12999) Jul.  = JD -3026604.5

End date           7 Jan 16800 CE Greg.                = JD 7857139.5


Note that not all asteroids can be computed for the whole period of Swiss Ephemeris.
The orbits of some of them are extremely sensitive to perturbations by major planets.
E.g. CHIRON, cannot be computed for the time before 650 CE and after 4650 CE because of close encounters with Saturn.
Outside this time range, Swiss Ephemeris returns the error code, an error message, and a position value 0.
Be aware, that the user will have to handle this case in his program.
Computing Chiron transits for Jesus or Alexander the Great will not work.

The same is true for Pholus before 3850 BCE, and for many other asteroids, as e.g. 1862 Apollo.
He becomes chaotic before the year 1870 CE, when he approaches Venus very closely.
Swiss Ephemeris does not provide positions of Apollo for earlier centuries !
 */
enum class UiCelPoints(val seId: Int, val category: CelPointCat, val geo: Boolean, val helio: Boolean,
                       val validFrom: Double, val validUntil: Double, val glyph: String, val rbKey: String) {
    SUN(0, CelPointCat.CLASSIC, true, false,-3026604.5, 7857139.5, "a","lshr.celpoints.sun"),
    MOON(1, CelPointCat.CLASSIC, true, false,-3026604.5, 7857139.5,"b","lshr.celpoints.moon"),
    MERCURY(2, CelPointCat.CLASSIC, true, true, -3026604.5, 7857139.5,"c","lshr.celpoints.mercury"),
    VENUS(3, CelPointCat.CLASSIC, true, true,-3026604.5, 7857139.5,"d","lshr.celpoints.venus"),
    EARTH(14, CelPointCat.CLASSIC, false, true,-3026604.5, 7857139.5,"e","lshr.celpoints.earth"),
    MARS(4, CelPointCat.CLASSIC, true, true, -3026604.5, 7857139.5,"f","lshr.celpoints.mars"),
    JUPITER(5, CelPointCat.CLASSIC, true, true,-3026604.5, 7857139.5,"g","lshr.celpoints.jupiter"),
    SATURN(6, CelPointCat.CLASSIC, true, true,-3026604.5, 7857139.5,"h","lshr.celpoints.saturn"),
    URANUS(7, CelPointCat.MODERN, true, true,-3026604.5, 7857139.5,"i","lshr.celpoints.uranus"),
    NEPTUNE(8, CelPointCat.MODERN,true, true,-3026604.5, 7857139.5,"j","lshr.celpoints.neptune"),
    PLUTO(9, CelPointCat.MODERN, true, true,-3026604.5, 7857139.5,"k","lshr.celpoints.pluto"),
    MEAN_NODE(10, CelPointCat.MATH_POINT, true, false, -3026604.5, 7857139.5,"{","lshr.celpoints.meannode"),
    TRUE_NODE(11, CelPointCat.MATH_POINT, true, false,-3026604.5, 7857139.5,"{","lshr.celpoints.truenode"),
    MEAN_APOGEE(12, CelPointCat.MATH_POINT, true, false,-3026604.5, 7857139.5,",","lshr.celpoints.meanapogee"),
    OSCU_APOGEE(13, CelPointCat.MATH_POINT, true, false,-3026604.5, 7857139.5,".","lshr.celpoints.oscuapogee"),
    CHEIRON(15, CelPointCat.CENTAUR, true, true,-3026604.5, 7857139.5,"w","lshr.celpoints.cheiron"),
    PHOLUS(16, CelPointCat.CENTAUR, true, true,-3026604.5, 7857139.5,")","lshr.celpoints.pholus"),
    NESSUS(17066, CelPointCat.CENTAUR, true, true,-3026604.5, 7857139.5,"(","lshr.celpoints.nessus"),
    CERES(17, CelPointCat.ASTEROID,true, true,-3026604.5, 7857139.5,"_", "lshr.celpoints.ceres"),
    PALLAS(18, CelPointCat.ASTEROID, true, true,-3026604.5, 7857139.5,"û", "lshr.celpoints.pallas"),
    JUNO(19, CelPointCat.ASTEROID, true, true,-3026604.5, 7857139.5,"ü","lshr.celpoints.juno"),
    VESTA(20, CelPointCat.ASTEROID, true, true,-3026604.5, 7857139.5,"À","lshr.celpoints.vesta"),
    HUYA(48628, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ï", "lshr.celpoints.huya"),
    MAKEMAKE(146472, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"î", "lshr.celpoints.makemake"),
    HAUMEA( 146108, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"í", "lshr.celpoints.haumea"),
    ERIS( 146199, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"*", "lshr.celpoints.eris"),
    IXION( 38978, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ó", "lshr.celpoints.ixion"),
    ORCUS( 100482, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ù", "lshr.celpoints.orcus"),
    QUAOAR( 60000, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ô",  "lshr.celpoints.quaoar"),
    SEDNA( 100377, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ö", "lshr.celpoints.sedna"),
    VARUNA(30000, CelPointCat.PLUTOID, true, true,-3026604.5, 7857139.5,"ò", "lshr.celpoints.varuna"),
}

enum class Zodiac(val rbKey: String) {
    TROPICAL("enum.zodiac.tropical"),
    SIDEREAL("enum.zodiac.sidereal")
}


