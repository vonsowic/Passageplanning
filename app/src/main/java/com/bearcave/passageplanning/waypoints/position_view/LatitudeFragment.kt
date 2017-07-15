package com.bearcave.passageplanning.waypoints.position_view

/**
 *
 * @author Michał Wąsowicz
 * @since 15.07.17
 * @version 1.0
 */
class LatitudeFragment : PositionFragment() {
    override val unsigned: String
        get() = "N"
    override val signed: String
        get() = "S"
}