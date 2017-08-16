package com.bearcave.passageplanning.passages.database

import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.base.database.DatabaseElement
import com.bearcave.passageplanning.routes.database.Route
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatterBuilder

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.1
 */
data class Passage(
        override val id: Int,
        val route: Route,
        val dateTime: DateTime,
        val speed: Float,
        val draught: Float) :
        DatabaseElement,
        Parcelable {

    override val name: String
        get() = "Route: ${route.name}\n${dateTime.toString(timeFormatter)}"

    constructor(parcel: Parcel): this(
            parcel.readInt(),
            parcel.readParcelable(Route::class.java.classLoader),
            parcel.readSerializable() as DateTime,
            parcel.readFloat(),
            parcel.readFloat()
            )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeParcelable(route, flags)
        dest?.writeSerializable(dateTime)
        dest?.writeFloat(speed)
        dest?.writeFloat(draught)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Passage> = object : Parcelable.Creator<Passage> {
            override fun createFromParcel(source: Parcel): Passage = Passage(source)

            override fun newArray(size: Int): Array<Passage?> = arrayOfNulls(size)
        }

        private val timeFormatter = DateTimeFormatterBuilder().appendPattern("YY-MM-dd, HH:mm:ss").toFormatter()
    }
}