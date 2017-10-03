package com.bearcave.passageplanning.tides.database

/**
 *
 * @author Michał Wąsowicz
 * @since 02.10.17
 * @version 1.0
 */
data class TideCurrentInfoHandler(
        val hoursToHighWater:   Int, // value between -6..6. Number of hours to the closest high water
        val lowWater:           Float,
        val highWater:          Float
)