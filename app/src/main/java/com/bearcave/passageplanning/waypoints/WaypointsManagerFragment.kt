package com.bearcave.passageplanning.waypoints


import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerWithIntKeyFragment
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD


class WaypointsManagerFragment : BaseManagerWithIntKeyFragment<Waypoint>(), WaypointCRUD {

    override val databaseKey: Int
        get() = WaypointCRUD.ID

    override val editorClass: Class<out WaypointEditorActivity>
        get() = WaypointEditorActivity::class.java

    override fun createAdapter(): BaseManagerAdapter<Waypoint, Int> =
            WaypointsManagerAdapter(this, context)

    override val title: String
        get() = getString(R.string.waypoints_menu)
}
