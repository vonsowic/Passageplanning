package com.bearcave.passageplanning.passage_monitor

import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime


/**
 *
 * @author Michał Wąsowicz
 * @since 02.07.17
 * @version 1.0
 */
class PassagePlan(val name: String, val date: DateTime, waypoints: List<Waypoint>) {

    val linkedWaypoints = ArrayList<LinkedWaypoint>(waypoints.size)

    init {
        waypoints.mapTo(linkedWaypoints) { LinkedWaypoint(it) }
    }

    constructor(passage: Passage, waypoints: List<Waypoint>)
            : this(passage.name, passage.dateTime, waypoints)


}