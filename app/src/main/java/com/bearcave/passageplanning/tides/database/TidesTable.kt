package com.bearcave.passageplanning.tides.database

import android.content.ContentValues
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.data.database.ManagerListener
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.utils.Gauge
import org.joda.time.DateTime
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

    override fun read(id: DateTime): TideItem {
        try {
            return super.read(
                    id
                            .minusSeconds(id.secondOfMinute)      // time in database is saved without seconds ( seconds are 0s).
                            .minusMinutes(id.minuteOfHour % Settings.getDownloadingConfiguration(Gauge.MARGATE).step.value)
            )
        } catch (e: CursorIndexOutOfBoundsException){
            throw TideNotInDatabaseException()
        }
    }

    fun removeExpired() {
        writableDatabase.execSQL(
                "DELETE FROM $tableName WHERE $KEY_TIME <= date('now','-1 day')"
        )
    }


    override val idKey: String
        get() = KEY_TIME

    companion object {

        private val KEY_PREDICTED = "predicted_tide_depth"
        private val KEY_TIME = "date_time"
    }
}
