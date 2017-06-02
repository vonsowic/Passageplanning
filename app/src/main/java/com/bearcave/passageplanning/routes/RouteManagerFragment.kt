package com.bearcave.passageplanning.routes


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.annotation.RequiresPermission
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.base.database.CRUD
import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.editor.RouteEditorActivity
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import java.util.*


class RouteManagerFragment : BaseManagerFragment<Route, Int>(), RouteCRUD, ReadWaypoints {


    override val databaseKey: Int
        get() = RouteCRUD.ID

    private var waypointsHolder: ReadWaypoints? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        waypointsHolder = context as ReadWaypoints
    }

    override val title: Int
        get() = R.string.routes_menu


    override val editorClass: Class<out BaseEditorActivity<Route>>
        get() = RouteEditorActivity::class.java


    override fun putExtra(mail: Intent) {
        val waypoints = readAllWaypoints() as ArrayList<Waypoint>

        mail.putParcelableArrayListExtra(
                RouteEditorActivity.WAYPOINTS_KEY,
                waypoints
        )
    }

    override fun createAdapter(): BaseManagerAdapter<Route, Int> = RouteManagerAdapter(this, context)


    override fun readAllWaypoints(): List<Waypoint> {
        return waypointsHolder!!.readAllWaypoints()
    }
}
