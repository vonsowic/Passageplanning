package com.bearcave.passageplanning.waypoints

import org.jsoup.nodes.Element

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

    fun get(i: Int): Waypoint {
        return list.get(i)
    }

    fun size(): Int = list.size

    fun add(waypoint: Waypoint) = list.add(waypoint)

    fun iterator() = list.iterator()

}