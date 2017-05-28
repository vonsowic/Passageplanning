package com.bearcave.passageplanning.passage.database

import android.os.Parcel
import android.os.Parcelable
import com.bearcave.passageplanning.base.database.DatabaseElement
import com.bearcave.passageplanning.routes.database.route.RouteDAO
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
data class PassageDao(val id: Int, val route: RouteDAO, val dateTime: DateTime, val speed: Float) :
        DatabaseElement,
        Parcelable {

    override fun getName(): String = "${route.name}\n$dateTime".replace("T", "\n")

    override fun getId(): Int = id

    constructor(parcel: Parcel): this(
            parcel.readInt(),
            parcel.readParcelable(RouteDAO::class.java.classLoader),
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
        @JvmField final val CREATOR: Parcelable.Creator<PassageDao> = object : Parcelable.Creator<PassageDao> {
            override fun createFromParcel(source: Parcel): PassageDao{
                return PassageDao(source)
            }

            override fun newArray(size: Int): Array<PassageDao?> {
                return arrayOfNulls(size)
            }
        }
    }
}