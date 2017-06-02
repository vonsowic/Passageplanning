package com.bearcave.passageplanning.routes

import com.bearcave.passageplanning.waypoints.database.Waypoint

/**
 * Created by miwas on 24.05.17.
 */

interface ReadWaypoints {
    fun readAllWaypoints(): List<Waypoint>
}
