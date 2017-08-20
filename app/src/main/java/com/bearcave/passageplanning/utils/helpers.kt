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

