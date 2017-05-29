package com.bearcave.passageplanning.routes.database


import android.os.Parcel
import android.os.Parcelable

import com.bearcave.passageplanning.base.database.DatabaseElement

import java.util.ArrayList
import java.util.Arrays


open class Route(override val id: Int, override val name: String, val waypointsIds: ArrayList<Int>) : DatabaseElement, Parcelable {

    override fun toString(): String {
        return Route.fromList(this)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeList(waypointsIds)
    }

    private constructor(`in`: Parcel) : this(
            `in`.readInt(),
            `in`.readString(),
            `in`.readArrayList(Route::class.java.classLoader) as ArrayList<Int>
    )

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<Route> = object : Parcelable.Creator<Route> {
            override fun createFromParcel(source: Parcel): Route {
                return Route(source)
            }

            override fun newArray(size: Int): Array<Route?> {
                return arrayOfNulls(size)
            }
        }

        fun fromString(waypoints: String): ArrayList<Int> {
            val waypointsIds = ArrayList<Int>()
            for (item in Arrays.asList(*waypoints.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())) {
                waypointsIds.add(Integer.valueOf(item))
            }
            return waypointsIds
        }

        fun fromList(route: Route): String {
            return Route.fromList(route.waypointsIds)
        }

        fun fromList(ids: ArrayList<Int>): String {
            val joiner = StringBuilder()
            for (id in ids) {
                joiner.append(id)
                joiner.append(",")
            }

            return joiner.toString()
        }
    }
}
