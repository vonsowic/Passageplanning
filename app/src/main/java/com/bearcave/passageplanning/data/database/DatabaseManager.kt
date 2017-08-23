package com.bearcave.passageplanning.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.SparseArray
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.passages.database.AccessToRouteTable
import com.bearcave.passageplanning.passages.database.PassageCRUD
import com.bearcave.passageplanning.passages.database.PassageTable
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.database.RouteTable
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable


class DatabaseManager(context: Context, databaseName: String) : SQLiteOpenHelper(DatabaseContext(context), databaseName, null, VERSION), ManagerListener, AccessToRouteTable {

    private val tables = SparseArray<Lazy<BaseTableWithCustomKey<*, *>>>()

    init {
        tables.put(WaypointCRUD.ID, lazy{ WaypointsTable(this) })
        tables.put(RouteCRUD.ID, lazy{ RouteTable(this)})
        tables.put(PassageCRUD.ID, lazy{ PassageTable(this) })

        for (gauge in Gauge.values()) {
            tables.put(gauge.id, lazy{ TidesTable(this, gauge)})
        }

        DATABASE_MANAGER_HANDLER = this
    }

    override fun onCreate(db: SQLiteDatabase) {
        (0 until tables.size())
                .map { tables.keyAt(it) }
                .forEach {
                    db.execSQL(tables.get(it).value.tableCreator())
                }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion == 24){
            val table = tables[PassageCRUD.ID].value
            db.execSQL("ALTER TABLE ${table.tableName} ADD COLUMN ${PassageTable.KEY_DRAUGHT} ${BaseTableWithCustomKey.FLOAT} DEFAULT 0.0;")
        } else {
            onCreate(db)
        }
    }

    fun getTable(tableId: Int): BaseTableWithCustomKey<*, *> = tables.get(tableId).value

    override fun readRoute(id: Int): Route = (tables.get(RouteCRUD.ID).value as RouteTable).read(id)

    companion object {
        private val VERSION = 26
        //private val VERSION = 24


        private var DATABASE_MANAGER_HANDLER: DatabaseManager? = null
        val DATABASE_MANAGER: DatabaseManager
            get() = DATABASE_MANAGER_HANDLER!!
    }
}