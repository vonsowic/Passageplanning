package com.bearcave.passageplanning.passage.database

import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.base.database.DatabaseElement
import com.bearcave.passageplanning.routes.database.Route
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
data class Passage(override val id: Int, val route: Route, val dateTime: DateTime, val speed: Float) :
        DatabaseElement,
        Parcelable {

    override val name: String
        get() = "${route.name}\n$dateTime\nSpeed: $speed".replace("T", "\n")

    constructor(parcel: Parcel): this(
            parcel.readInt(),
            parcel.readParcelable(Route::class.java.classLoader),
            parcel.readSerializable() as DateTime,
            parcel.readFloat()
            )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeParcelable(route, flags)
        dest?.writeSerializable(dateTime)
        dest?.writeFloat(speed)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Passage> = object : Parcelable.Creator<Passage> {
            override fun createFromParcel(source: Parcel): Passage {
                return Passage(source)
            }

            override fun newArray(size: Int): Array<Passage?> {
                return arrayOfNulls(size)
            }
        }
    }
}