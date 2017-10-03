package com.bearcave.passageplanning.passages.planner

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.util.SparseArray
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.itextpdf.text.Document
import com.itextpdf.text.html.simpleparser.HTMLWorker
import com.itextpdf.text.pdf.PdfWriter
import j2html.TagCreator.*
import j2html.tags.ContainerTag
import org.joda.time.DateTime
import java.io.File
import java.io.FileOutputStream
import java.io.StringReader


/**
 * @author Michał Wąsowicz
 * @since 16.08.17
 * @version 1.1
 */
class PassagePlan(
        val passage: Passage,
        private val waypoints: List<Waypoint>
) : Parcelable {


    val lastWaypoint
        get() = waypoints.last()

    val etaAtEnd: DateTime
        get() = eta(size-1)

    val size
        get()=waypoints.size


    operator fun get(position: Int) = waypoints[position]


    var speed
        get() = passage.speed
        set(value) {
            passage.speed = value
            speedsHandler.clear()
            etasHandler.clear()

            Log.e("PassagePlan", "${etasHandler.size()}")
        }


    /**
     * Contains datetimes of already computed waypoints.
     */
    private val etasHandler = SparseArray<DateTime>()


    private val speedsHandler = SparseArray<Float>()



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
     * @return ukc
     */
    fun ukc(i: Int) = waypoints[i].ukc + predictedTideHeight(i) - passage.draught


    /**
     * Estimated time arrival.
     * @param i index of waypoint
     */
    fun eta(i: Int): DateTime {
        if(etasHandler.indexOfKey(i) < 0){
            if( i==0 ){
                etasHandler.put(i, passage.dateTime)
            } else {
                etasHandler.put(
                        i,
                        eta(i-1)                                            // eta at previous waypoint
                                .plusSeconds((dist(i-1) / speedAt(i-1)).toInt()))   // plus time between this and previous waypoint
            }
        }

        return etasHandler.get(i)
    }


    /**
     * @param i index of waypoint
     * @return speed to the next waypoint
     */
    fun speedAt(i: Int) = passage.speed + speedOfCurrent(i)


    private fun speedOfCurrent(index: Int): Float {
        if(speedsHandler.get(index) == null) {
            speedsHandler.put(
                    index,
                    this[index].tideCurrentStation.getValue(                                                                    // get value
                            (DatabaseManager.DATABASE_MANAGER.getTable(this[index].tideCurrentStation.gaugeId) as TidesTable)   // based on database
                                    .getTideCurrentInfo(eta(index))                                                             // computations
                    )


            )
        }

        return speedsHandler[index]
    }

    /**
     * @param i index of waypoint for which tide table is returned.
     */
    private fun getTideTable(i: Int) = DatabaseManager
            .DATABASE_MANAGER
            .getTable(waypoints[i].gauge.id) as TidesTable


    /**
     * @param i index of waypoint for which tide height is returned.
     */
    private fun predictedTideHeight(i: Int) = getTideTable(i)
            .read(eta(i))
            .predictedTideHeight



    fun toHTML(context: Context) = document(
            html(
                    head(
                            title(context.getString(R.string.app_name))
                    ),
                    body(
                            table(
                                    waypoints
                                            .withIndex()
                                            .map {
                                                tr(
                                                        td("${it.index}"),
                                                        td(it.value.name),
                                                        td(it.value.characteristic),
                                                        td("${course(it.index)}"),
                                                        td(eta(it.index).toString("HH:mm")),
                                                        td("${dist(it.index)/Settings.NAUTICAL_MILE}"),
                                                        td("${toGo(it.index)/Settings.NAUTICAL_MILE}"),
                                                        td("${it.value.ukc}"),
                                                        td("${ukc(it.index)}".replace("-1.0", "tide height not available"))
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
                                                            th("UKC [m]")
                                                    ),
                                                    ContainerTag::with
                                            )
                            ).attr("border", "1"),
                            i("Created by the best son in the world")
                    )
            )
    ) ?: "Document could not be created."


    /**
     * Saves passage plan to pdf file in cache directory.
     * @return pdf file saved in cache.
     */
    fun toPDF(context: Context, html: String = toHTML(context)): File {
        val tmpFile = File.createTempFile(passage.name, "pdf", context.cacheDir)
        val pdfFileWriter = FileOutputStream(tmpFile)

        val document = Document()
        PdfWriter.getInstance(document, pdfFileWriter)
        document.open()

        val htmlWorker = HTMLWorker(document)
        htmlWorker.parse(StringReader(html))

        document.close()
        pdfFileWriter.close()

        return tmpFile
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