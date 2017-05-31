package com.bearcave.passageplanning.waypoints.database

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.base.database.DatabaseElement
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import java.io.Serializable


/**
 * Data structure representing single waypoint.
 * UKC in metres.
 * Latitude in decimal degrees.
 * Longitude in decimal degrees.
 */
data class Waypoint(
        override val id: Int,
        override val name: String,
        val note: String,
        val characteristic: String,
        val ukc: Float,
        val latitude: Double,
        val longitude: Double,
        val gauge: Gauge) : Parcelable, Serializable, DatabaseElement {


    constructor(id:Int, name: String, note: String, characteristic: String, ukc: Float, latitude: String, longitude: String, gauge: Gauge):
            this(id, name, note, characteristic, ukc, Location.convert(latitude), Location.convert(longitude), gauge)


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


    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readDouble(),
            parcel.readDouble(),
            Gauge.getById(parcel.readInt())
    )

    override fun writeToParcel(dest: Parcel, flag: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(note)
        dest.writeString(characteristic)
        dest.writeFloat(ukc)
        dest.writeDouble(latitude)
        dest.writeDouble(longitude)
        dest.writeInt(gauge.id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Waypoint> = object : Parcelable.Creator<Waypoint> {
            override fun createFromParcel(source: Parcel): Waypoint {
                return Waypoint(source)
            }

            override fun newArray(size: Int): Array<Waypoint?> {
                return arrayOfNulls(size)
            }
        }
    }
}


