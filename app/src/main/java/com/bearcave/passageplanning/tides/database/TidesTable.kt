package com.bearcave.passageplanning.tides.database

import android.content.ContentValues
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.data.database.ManagerListener
import com.bearcave.passageplanning.tides.utils.Gauge
import org.joda.time.DateTime
import org.joda.time.Duration
import java.lang.Math.abs
import java.util.*


class TidesTable(manager: ManagerListener, gauge: Gauge) : BaseTableWithCustomKey<TideItem, DateTime>(manager), TideCRUD {

    private val gauge: String = gauge.humanCode

    override val tableName: String
        get() = gauge.replace(" ", "_")


    override fun createKeyToValueTypeHolder(): LinkedHashMap<String, String> {
        val requestedTypeHolder = LinkedHashMap<String, String>()
        requestedTypeHolder.put(KEY_TIME, BaseTableWithCustomKey.Companion.DATETIME + BaseTableWithCustomKey.Companion.PRIMARY_KEY + BaseTableWithCustomKey.Companion.NOT_NULL)
        requestedTypeHolder.put(KEY_PREDICTED, BaseTableWithCustomKey.Companion.FLOAT + BaseTableWithCustomKey.Companion.NOT_NULL)
        return requestedTypeHolder
    }

    override fun getContentValue(element: TideItem): ContentValues {
        val values = ContentValues()
        values.put(KEY_TIME, element.id.toString())
        values.put(KEY_PREDICTED, element.predictedTideHeight)
        return values
    }

    override fun loadFrom(cursor: Cursor) = TideItem(
                DateTime.parse(cursor.getString(0)),
                cursor.getFloat(1)
        )


    override fun insertAll(tides: Collection<TideItem>) {
        val database = writableDatabase

        tides.forEach {
            database.insertWithOnConflict(
                    tableName,
                    null,
                    getContentValue(it),
                    SQLiteDatabase.CONFLICT_REPLACE
            )
        }
    }

    override fun read(id: DateTime) = try {
            super.read(
                    id
                            .withSecondOfMinute(0)
                            .withMillisOfSecond(0)
            )
        } catch (e: CursorIndexOutOfBoundsException){
            throw TideNotInDatabaseException()
        }


    fun removeExpired() {
        writableDatabase.execSQL(
                "DELETE FROM $tableName WHERE $KEY_TIME <= date('now','-1 day')"
        )
    }

    fun readByRange(time: DateTime, hoursAsRange: Int): ArrayList<TideItem>{
        val selectedTides = ArrayList<TideItem>()
        val selectQuery = "SELECT * FROM $tableName WHERE $KEY_TIME BETWEEN '${time.minusHours(hoursAsRange)}' and '${time.plusHours(hoursAsRange)}'" // from range <time - 12h, time + 12h>
        //Log.e("QUERY:", selectQuery)
        val cursor = readableDatabase.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                selectedTides.add(loadFrom(cursor))
            } while (cursor.moveToNext())
        }

        return selectedTides
    }

    fun getTideCurrentInfo(time: DateTime): TideCurrentInfoHandler {
        var selectedTides = readByRange(time, 16)
        if (selectedTides.size < 120) throw TideNotInDatabaseException()

        selectedTides = selectedTides.filterOnlyTides() as ArrayList<TideItem>

        var meanHeight = 0f
        selectedTides.forEach { meanHeight += it.predictedTideHeight }
        meanHeight /= selectedTides.size


        val highWater = selectedTides
                .filter { it.predictedTideHeight >= meanHeight }
                .minBy{ abs(Duration(it.id, time).millis) }


        val lowWater = selectedTides
                .filter { it.predictedTideHeight < meanHeight }
                .minBy{ abs(Duration(it.id, time).millis) }

        Log.e("Selected High water: ", "${highWater?.id} - ${highWater?.predictedTideHeight}[m]")
        Log.e("Selected Low water: ", "${lowWater?.id} - ${lowWater?.predictedTideHeight}[m]")

        val duration = Duration(highWater?.id, time).standardHours.toInt()
        Log.e("Duration", duration.toString())
        return TideCurrentInfoHandler(
                duration,
                lowWater!!.predictedTideHeight,
                highWater!!.predictedTideHeight
        )
    }


    override val idKey: String
        get() = KEY_TIME

    companion object {

        private val KEY_PREDICTED = "predicted_tide_depth"
        private val KEY_TIME = "date_time"
    }
}
