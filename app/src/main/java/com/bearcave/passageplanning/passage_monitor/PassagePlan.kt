package com.bearcave.passageplanning.passage_monitor

import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime
import java.io.Serializable
import java.security.SecureRandom


/**
 *
 * @author Michał Wąsowicz
 * @since 02.07.17
 * @version 1.0
 */
class PassagePlan(val name: String, val date: DateTime, private val waypoints: List<Waypoint>) : Parcelable, Serializable {

    val lastWaypoint
        get() = waypoints.last()

    operator fun get(position: Int) = waypoints[position]

    constructor(passage: Passage, waypoints: List<Waypoint>)
            : this(passage.name, passage.dateTime, waypoints)

    /**
     * Distance to the last waypoint.
     */
    fun toGo(waypointPosition: Int) = this[waypointPosition].distanceTo(lastWaypoint)

    /**
     * Distance to the next waypoint.
     */
    fun dist(waypointPosition: Int) =
        if (waypointPosition == waypoints.size - 1) 0.0F   // if it is last waypoint the distance is 0
        else this[waypointPosition]
                .distanceTo(this[waypointPosition + 1])

    /**
     * Course to the next waypoint.
     */
    fun course(waypointPosition: Int) =
        if (waypointPosition == waypoints.size - 1) -1.0F    // if it is last waypoint the course is -1
        else this[waypointPosition]
                .bearingTo(this[waypointPosition + 1])


    fun toHTML() {

    }

    fun toPDF() {

    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeSerializable(date)
        parcel.writeTypedList(waypoints)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PassagePlan> {
        override fun createFromParcel(parcel: Parcel): PassagePlan {
            return PassagePlan(parcel)
        }

        override fun newArray(size: Int): Array<PassagePlan?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readSerializable() as DateTime,
            parcel.createTypedArrayList(Waypoint.CREATOR)
    )
}