package com.bearcave.passageplanning.base

import android.content.Context
import android.util.SparseArray
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import com.bearcave.passageplanning.tasks.BackgroundTask
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint

/**
 * BaseManagerAdapter with access to waypoints database.
 *
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 31.05.17
 */
abstract class BaseManagerAdapterWithWaypoints<Dao : DatabaseElementWithCustomKey<T>, T>(parent: BasePoorManagerFragment<*, *>, context: Context)
    : BaseManagerAdapter<Dao, T>(parent, context) {

    private val waypointsDatabase: ReadWaypoints = context as ReadWaypoints
    private val waypoints = SparseArray<Waypoint>()

    init {
        BackgroundTask(context).execute({
            val waypointsList = waypointsDatabase.readAllWaypoints()
            for (waypoint in waypointsList){
                waypoints.put(waypoint.id, waypoint)
            }
        })
    }

    protected fun getWaypointById(id: Int?): Waypoint = waypoints.get(id!!)

}
