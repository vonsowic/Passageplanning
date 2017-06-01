package com.bearcave.passageplanning.waypoints.database


import com.bearcave.passageplanning.base.database.CRUD

interface WaypointCRUD : CRUD<Waypoint> {
    companion object {
        val ID = 100
    }
}
