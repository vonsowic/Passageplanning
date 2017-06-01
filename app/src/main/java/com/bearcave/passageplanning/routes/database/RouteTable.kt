package com.bearcave.passageplanning.routes.database


import android.content.ContentValues
import android.database.Cursor
import com.bearcave.passageplanning.base.database.BaseTable
import com.bearcave.passageplanning.data.database.ManagerListener
import java.util.*

class RouteTable(manager: ManagerListener) : BaseTable<Route>(manager), RouteCRUD {

    override val tableName: String
        get() = "routes"

    override fun createKeyToValueTypeHolder(): LinkedHashMap<String, String> {
        val requestedTypeHolder = LinkedHashMap<String, String>()
        requestedTypeHolder.put(KEY_ID, INTEGER + PRIMARY_KEY + AUTOINCREMENT)
        requestedTypeHolder.put(KEY_NAME, TEXT + NOT_NULL)
        requestedTypeHolder.put(KEY_WAYPOINTS, DATETIME + NOT_NULL)
        return requestedTypeHolder
    }

    override fun getContentValue(element: Route): ContentValues {
        val value = ContentValues()
        value.put(KEY_NAME, element.name)
        value.put(KEY_WAYPOINTS, Route.fromList(element.waypointsIds))
        return value
    }

    override fun loadFrom(cursor: Cursor): Route {
        return Route(
                cursor.getInt(0),
                cursor.getString(1),
                Route.fromString(cursor.getString(2))
        )
    }

    companion object {

        private val KEY_NAME = "humanCode"
        private val KEY_WAYPOINTS = "waypoints_ids"
    }

}
