package com.bearcave.passageplanning.waypoints


import android.content.Context

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable


class WaypointsManagerFragment : BaseManagerFragment<Waypoint, Int>(), WaypointCRUD {

    override val databaseKey: Int
        get() = WaypointCRUD.ID

    override var adapter: BaseManagerAdapter<Waypoint, Int>
        get() = adapter
        set(value) { adapter = value }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder = context as OnDatabaseRequestedListener?
        database = databaseHolder!!.onGetTableListener(WaypointCRUD.ID) as WaypointsTable
    }

    override val editorClass: Class<out WaypointEditorActivity>
        get() = WaypointEditorActivity::class.java

    override fun onDataCreated(result: Waypoint) {
        insert(result)
        adapter.add(result)
    }

    override fun onDataUpdated(result: Waypoint) {
        database!!.update(result)
        adapter.update(result)
    }

    override fun createAdapter(): BaseManagerAdapter<Waypoint, Int> {
        return WaypointsManagerAdapter(this, context)
    }

    override val title: Int
        get() = R.string.waypoints_menu
}
