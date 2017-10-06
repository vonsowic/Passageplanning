package com.bearcave.passageplanning.utils

import java.math.BigDecimal
import java.math.RoundingMode

/**
 *
 * @author Michał Wąsowicz
 * @since 17.08.17
 * @version 1.0
 */
fun round(value: Double, places: Int = 2): Double {
    if (places < 0) throw IllegalArgumentException()

    var bd = BigDecimal(value)
    bd = bd.setScale(places, RoundingMode.HALF_UP)
    return bd.toDouble()
}

fun round(value: Float, places: Int = 2) = round(value.toDouble(), places)

/**
 * Converts value treated as kts to m/s
 */
fun Float.convertFromKtsToMs() = this * 0.514444f

/**
 * Converts value treated as m/s to kts
 */
fun Float.convertFromMsToKts() = this * 1.943844f

fun Float.convertFromMmToM() = this * 1852

fun Float.convertFromMToMm() = this * 0.0005399568034557236f

