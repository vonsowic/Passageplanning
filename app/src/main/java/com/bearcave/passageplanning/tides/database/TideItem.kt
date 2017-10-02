package com.bearcave.passageplanning.tides.database

import android.os.Parcel
import android.os.Parcelable

import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import org.joda.time.DateTime


data class TideItem(override val id: DateTime, val predictedTideHeight: Float) : Parcelable, DatabaseElementWithCustomKey<DateTime> {

    override val name: String
        get() = "$id : $predictedTideHeight"


    private constructor(`in`: Parcel): this(
            `in`.readSerializable() as DateTime,
            `in`.readFloat()
    )


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeSerializable(id)
        dest.writeFloat(predictedTideHeight)
    }

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<TideItem> = object : Parcelable.Creator<TideItem> {
            override fun createFromParcel(`in`: Parcel) = TideItem(`in`)

            override fun newArray(size: Int): Array<TideItem?> = arrayOfNulls(size)
        }
    }
}
