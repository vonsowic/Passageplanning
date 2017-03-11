package com.bearcave.passageplanning.list_of_waypoints

/**
 * @author Michał Wąsowicz
 */
class WaypointsList {

    private val list: ArrayList<Waypoint>

    constructor() {
        list = ArrayList<Waypoint>()
    }

    constructor(size: Int) {
        list = ArrayList<Waypoint>(size)
    }

    fun size(): Int = list.size

    fun add(waypoint: Waypoint) = list.add(waypoint)

    fun iterator() = list.iterator()

}