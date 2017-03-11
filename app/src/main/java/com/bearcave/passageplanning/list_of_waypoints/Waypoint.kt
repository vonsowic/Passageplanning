package com.bearcave.passageplanning.list_of_waypoints

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by miwas on 10.03.17.
 */
class Waypoint(
        val waypointName: String,
        val ukc: String,
        val bearing: Int,
        val characteristic: String,
        val position: Location): Serializable