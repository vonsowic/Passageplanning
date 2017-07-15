package com.bearcave.passageplanning.waypoints.position_view

/**
 *
 * @author Michał Wąsowicz
 * @since 15.07.17
 * @version 1.0
 */
class LongitudeFragment : PositionFragment() {

    override val unsigned: String
        get() = "E"

    override val signed: String
        get() = "W"
}