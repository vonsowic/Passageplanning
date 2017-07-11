package com.bearcave.passageplanning.passage_monitor

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.waypoints.database.Waypoint
import j2html.TagCreator.*
import j2html.tags.ContainerTag


/**
 * @author Michał Wąsowicz
 * @since 02.07.17
 * @version 1.0
 */
class PassagePlan(
        val passage: Passage,
        private val waypoints: List<Waypoint>
) : Parcelable {

    val lastWaypoint
        get() = waypoints.last()

    operator fun get(position: Int) = waypoints[position]

    /**
     * @return distance to the last waypoint in meters.
     */
    fun toGo(waypointPosition: Int) = waypoints
            .withIndex()
            .filter { it.index >= waypointPosition }
            .map { dist(it.index) }
            .sum()

    /**
     * @return distance to the next waypoint in meters.
     */
    fun dist(waypointPosition: Int) =
        if (waypointPosition == waypoints.lastIndex) 0F   // if it is last waypoint the distance is 0
        else this[waypointPosition]
                .distanceTo(this[waypointPosition + 1])

    /**
     * @return course to the next waypoint in degrees.
     */
    fun course(waypointPosition: Int) =
        if (waypointPosition == waypoints.lastIndex) 0F    // if it is last waypoint the course is 0
        else this[waypointPosition]
                .bearingTo(this[waypointPosition + 1])

    /**
     * @param i index of waypoint
     */
    fun actualDepth(i: Int) = waypoints[i].ukc


    /**
     * Estimated times between waypoints in seconds.
     */
    private fun timesBetween() = waypoints
            .withIndex()
            .map { dist(it.index) / passage.speed }



    /**
     * Estimated time to the last waypoint in seconds.
     * @param i index of waypoint
     */
    private fun timeToEnd(i: Int) = timesBetween()
            .subList(i, waypoints.size)
            .sum()


    /**
     * Estimated time to the last waypoint in seconds.
     */
    private fun timesToEnd() = (0..waypoints.lastIndex)
            .map { timeToEnd(it) }



    private fun etas() = timesToEnd()
            .map { etaAtEnd().minusSeconds(it.toInt()) }

    /**
     * Estimated time arrival.
     * @param i index of waypoint
     */
    fun eta(i: Int) = etas()[i]!!


    fun etaAtEnd() = passage
            .dateTime
            .plusSeconds(
                    timeToEnd(0).toInt()
            )



    fun toHTML(context: Context) = document(
        html(
            head(
                title(context.getString(R.string.app_name))
            ),
            body(
                 table(
                        waypoints
                                .withIndex()
                                .map { tr(
                                        td("${it.index}"),
                                        td(it.value.name),
                                        td(it.value.characteristic),
                                        td("${course(it.index)}"),
                                        td(eta(it.index).toString("HH:mm")),
                                        td("${dist(it.index)/Settings.NAUTICAL_MILE}"),
                                        td("${toGo(it.index)/Settings.NAUTICAL_MILE}"),
                                        td("${it.value.ukc}"),
                                        td("${actualDepth(it.index)}")
                                        )
                                }
                                .fold(
                                        tr(
                                                th("NO"),
                                                th("WPT"),
                                                th("CHARACTERISTIC"),
                                                th("COURSE"),
                                                th("ETA"),
                                                th("DIST [Mm]"),
                                                th("TO GO [Mm]"),
                                                th("UKC [m]"),
                                                th("ACTUAL [m]")
                                        ),
                                        ContainerTag::with
                                )
                 ).attr("border", "1"),
                 i("Created by the best son in the world")
            )
        )
    )


    fun toPDF() {

    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(passage, flags)
        parcel.writeTypedList(waypoints)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<PassagePlan> {
        override fun createFromParcel(parcel: Parcel) = PassagePlan(parcel)
        override fun newArray(size: Int): Array<PassagePlan?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel) : this(
            parcel.readParcelable<Passage>(Passage::class.java.classLoader),
            parcel.createTypedArrayList(Waypoint.CREATOR)
    )
}