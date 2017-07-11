package com.bearcave.passageplanning.passage_monitor

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime
import j2html.TagCreator.*
import j2html.TagCreator.attrs
import j2html.tags.ContainerTag
import org.jsoup.select.Collector
import org.jsoup.select.Collector.collect
import java.util.stream.Collectors


/**
 * @author Michał Wąsowicz
 * @since 02.07.17
 * @version 1.0
 */
class PassagePlan(val name: String, val date: DateTime, private val waypoints: List<Waypoint>) : Parcelable {

    val lastWaypoint
        get() = waypoints.last()

    operator fun get(position: Int) = waypoints[position]

    constructor(passage: Passage, waypoints: List<Waypoint>)
            : this(passage.name, passage.dateTime, waypoints)

    /**
     * @return distance to the last waypoint.
     */
    fun toGo(waypointPosition: Int) = waypoints
            .withIndex()
            .filter { it.index >= waypointPosition }
            .map { dist(it.index) }
            .sum()

    /**
     * @return distance to the next waypoint.
     */
    fun dist(waypointPosition: Int) =
        if (waypointPosition == waypoints.size - 1) 0.0F   // if it is last waypoint the distance is 0
        else this[waypointPosition]
                .distanceTo(this[waypointPosition + 1])

    /**
     * @return course to the next waypoint.
     */
    fun course(waypointPosition: Int) =
        if (waypointPosition == waypoints.size - 1) -1.0F    // if it is last waypoint the course is -1
        else this[waypointPosition]
                .bearingTo(this[waypointPosition + 1])


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
                                    td(it.value.name),
                                    td(it.value.characteristic),
                                    td(course(it.index).toString()),
                                    td(dist(it.index).toString()),
                                    td(toGo(it.index).toString())
                                    )
                            }
                            .fold(
                                tr(
                                    th("WPT"),
                                    th("CHARACTERISTIC"),
                                    th("BEARING TO"),
                                    th("DIST"),
                                    th("TO GO")
                                    ),
                                ContainerTag::with
                            )
                 )
            )
        )
    )



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