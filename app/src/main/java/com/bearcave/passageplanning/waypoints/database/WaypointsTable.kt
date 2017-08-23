package com.bearcave.passageplanning.waypoints.database


import android.content.ContentValues
import android.database.Cursor
import com.bearcave.passageplanning.base.database.BaseTable
import com.bearcave.passageplanning.data.database.ManagerListener
import com.bearcave.passageplanning.tides.utils.Gauge
import java.util.*


class WaypointsTable(manager: ManagerListener) : BaseTable<Waypoint>(manager) {

    override val tableName: String
        get() = "waypoints"

    override fun createKeyToValueTypeHolder(): LinkedHashMap<String, String> {
        val typeHolder = LinkedHashMap<String, String>()

        typeHolder.put(KEY_ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT)
        typeHolder.put(KEY_NAME, TEXT + NOT_NULL + UNIQUE)
        typeHolder.put(KEY_NOTE, TEXT)
        typeHolder.put(KEY_CHARACTERISTIC, TEXT)
        typeHolder.put(KEY_UKC, FLOAT + NOT_NULL)
        typeHolder.put(KEY_LATITUDE, DOUBLE + NOT_NULL)
        typeHolder.put(KEY_LONGITUDE, DOUBLE + NOT_NULL)
        typeHolder.put(KEY_GAUGE, INTEGER + NOT_NULL)

        return typeHolder
    }


    override fun getContentValue(element: Waypoint): ContentValues {
        val values = ContentValues()
        values.put(KEY_NAME, element.name)
        values.put(KEY_NOTE, element.note)
        values.put(KEY_CHARACTERISTIC, element.characteristic)
        values.put(KEY_UKC, element.ukc)
        values.put(KEY_LATITUDE, element.latitude)
        values.put(KEY_LONGITUDE, element.longitude)
        values.put(KEY_GAUGE, element.gauge.id)
        return values
    }

    override fun loadFrom(cursor: Cursor): Waypoint {
        var i = 0
        return Waypoint(
                cursor.getInt(i++), // id
                cursor.getString(i++), // humanCode
                cursor.getString(i++), // note
                cursor.getString(i++), // characteristic
                cursor.getFloat(i++), // ukc
                cursor.getDouble(i++), // latitude
                cursor.getDouble(i++), // longitude
                Gauge.getById(cursor.getInt(i))   // gauge
        )
    }

    companion object {

        val KEY_ID = "id"
        val KEY_NAME = "humanCode"
        val KEY_CHARACTERISTIC = "characteristic"
        val KEY_NOTE = "note"
        val KEY_UKC = "ukc"
        val KEY_LONGITUDE = "longitude"
        val KEY_LATITUDE = "latitude"
        val KEY_GAUGE = "gauge_id"
    }
}
