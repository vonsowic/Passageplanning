package com.bearcave.passageplanning.waypoints

import org.jsoup.nodes.Element

/**
 * @author Michał Wąsowicz
 */
class WaypointsList {

    private val list: ArrayList<Waypoint>

    constructor() {
        list = ArrayList<Waypoint>()

        val w = Waypoint(Element("waypoint"))

    }

    constructor(size: Int) {
        list = ArrayList<Waypoint>(size)
    }

    fun size(): Int = list.size

    fun add(waypoint: Waypoint) = list.add(waypoint)

    fun iterator() = list.iterator()

}