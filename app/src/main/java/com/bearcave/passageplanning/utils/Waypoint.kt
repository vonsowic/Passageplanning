package com.bearcave.passageplanning.utils

import android.location.Location
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge
import java.io.Serializable


/**
 * Data structure representing single waypoint.
 */
open class Waypoint: Serializable {

    var name: String = ""
        private set
    var characteristic: String = ""
        private set
    var note: String = ""
        private set

    /**
     * UKC in metres.
     */
    var ukc: Float = 0F
        private set

    /**
     * Latitude in decimal degrees.
     */
    var latitude = 0.0
        private set

    /**
     * Longitude in decimal degrees.
     */
    var longitude = 0.0
        private set

    var gauge: Gauge = Gauge.MARGATE
        private set


    constructor(name: String,
                characteristic: String,
                ukc: Float,
                latitude: Double,
                longitude: Double,
                gauge: Gauge){

        this.name = name
        this.characteristic = characteristic
        this.ukc = ukc
        this.latitude = latitude
        this.longitude = longitude
        this.gauge = gauge

    }

    constructor(name: String, characteristic: String, ukc: Float, latitude: String, longitude: String, gauge: Gauge):
            this(name, characteristic, ukc, Location.convert(latitude), Location.convert(longitude), gauge)

    constructor(name: String, note: String, characteristic: String, ukc: Float, latitude: String, longitude: String, gauge: Gauge):
            this(name, note, characteristic, ukc, Location.convert(latitude), Location.convert(longitude), gauge)

    constructor(name: String,
                note: String,
                characteristic: String,
                ukc: Float,
                latitude: Double,
                longitude: Double,
                gauge: Gauge):
            this(
                name,
                characteristic,
                ukc,
                latitude,
                longitude,
                gauge
            ){
                this.note = note
    }

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

    fun getLatitudeInSecondFormat(): String {
        return Location.convert(this.latitude, Location.FORMAT_SECONDS)
    }

    fun getLongitudeInSecondFormat(): String {
        return Location.convert(this.longitude, Location.FORMAT_SECONDS)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Waypoint) return false

        if (name != other.name) return false
        if (characteristic != other.characteristic) return false
        if (note != other.note) return false
        if (ukc != other.ukc) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (gauge != other.gauge) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + characteristic.hashCode()
        result = 31 * result + note.hashCode()
        result = 31 * result + ukc.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + gauge.hashCode()
        return result
    }


}