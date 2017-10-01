package com.bearcave.passageplanning.passages.planner

import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 28.09.17
 * @version 1.0
 */
data class PlanElement(val waypoint: Waypoint, val eta: DateTime)