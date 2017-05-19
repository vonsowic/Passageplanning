package com.bearcave.passageplanning.waypoints

import android.location.Location
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge
import java.io.Serializable


/**
 * Data structure representing single waypoint.
 */
class Waypoint: Serializable {

    var id: Int = -1
    var name: String = ""
    var characteristic: String = ""
    var note: String = ""
    var ukc: Float = 0F
    var latitude = 0.0
    var longitude = 0.0
    var gauge: Gauge = Gauge.MARGATE


    constructor(id: Int,
                name: String,
                characteristic: String,
                ukc: Float,
                latitude: Double,
                longitude: Double,
                gauge: Gauge){

        this.id = id
        this.name = name
        this.characteristic = characteristic
        this.ukc = ukc
        this.latitude = latitude
        this.longitude = longitude
        this.gauge = gauge

    }

    constructor()


    /**
     * @return the approximate initial bearing in degrees East of true North when traveling along the shortest path between this location and the given location.
     */
    fun bearingTo(waypoint: Waypoint): Float {
        var angle = Math.toDegrees(Math.atan2(waypoint.longitude - this.longitude, waypoint.latitude - this.latitude)).toFloat()

        if (angle < 0) {
            angle += 360f
        }

        return angle
    }

    /**
     * @return the approximate distance in meters between this location and the given location.
     */
    fun distanceTo(waypoint: Waypoint): Float {
        /*return Math.sqrt(
                Math.pow(this.longitude-waypoint.longitude, 2.0) + Math.pow(this.latitude-waypoint.latitude, 2.0)
        )*/
        val result = FloatArray(3)
        Location.distanceBetween(this.latitude, this.longitude, waypoint.latitude, waypoint.longitude, result)
        return result[0]

    }

    override fun equals(other: Any?): Boolean {
        val wpt: Waypoint = other as Waypoint
        return id.equals(wpt.id)
                && (name == wpt.name)
                && characteristic.equals(wpt.characteristic)
                && longitude.equals(wpt.longitude)
                && latitude.equals(wpt.latitude)
    }
}