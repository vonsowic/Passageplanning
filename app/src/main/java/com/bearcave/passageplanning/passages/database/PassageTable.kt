package com.bearcave.passageplanning.passages.database

import android.content.ContentValues
import android.database.Cursor
import com.bearcave.passageplanning.base.database.BaseTable
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import org.joda.time.DateTime
import java.util.*

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */

class PassageTable(listener: AccessToRouteTable) : BaseTable<Passage>(listener) {

    override val tableName: String
        get() = "passages"

    override fun createKeyToValueTypeHolder(): LinkedHashMap<String, String> {
        val result = LinkedHashMap<String, String>()

        result.put(KEY_ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT )
        result.put(KEY_ROUTE_ID, INTEGER + NOT_NULL)
        result.put(KEY_DATE_AND_TIME, DATETIME + NOT_NULL)
        result.put(KEY_SPEED, FLOAT + NOT_NULL )
        result.put(KEY_DRAUGHT, FLOAT + NOT_NULL )

        return result
    }

    override fun getContentValue(element: Passage): ContentValues {
        val result = ContentValues()
        result.put(KEY_ROUTE_ID, element.route.id)
        result.put(KEY_DATE_AND_TIME, element.dateTime.toString())
        result.put(KEY_SPEED, element.speed)
        result.put(KEY_DRAUGHT, element.draught)
        return result
    }

    override fun createReferences(): ArrayList<Array<String>> {
        val result = ArrayList<Array<String>>()
        result.add(arrayOf(KEY_ROUTE_ID, "route", BaseTableWithCustomKey.KEY_ID))
        return result
    }

    override fun loadFrom(cursor: Cursor): Passage {
        return Passage(
                cursor.getInt(0),
                (manager as AccessToRouteTable).readRoute(cursor.getInt(1)),
                DateTime.parse(cursor.getString(2)),
                cursor.getFloat(3),
                cursor.getFloat(4)
        )
    }

    companion object {
        val KEY_ROUTE_ID = "route_id"
        val KEY_DATE_AND_TIME = "start_at"
        val KEY_SPEED = "speed"
        val KEY_DRAUGHT = "draught"
    }
}