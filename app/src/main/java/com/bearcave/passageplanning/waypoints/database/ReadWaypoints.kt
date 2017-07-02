package com.bearcave.passageplanning.waypoints.database

import com.bearcave.passageplanning.waypoints.database.Waypoint

/**
 *
 * @since 24.05.17.
 * @author Michał Wąsowicz
 * @version 1.0
 */

interface ReadWaypoints {
    fun readAllWaypoints(): List<Waypoint>
    fun readWith(ids: List<Int>): List<Waypoint>
}
