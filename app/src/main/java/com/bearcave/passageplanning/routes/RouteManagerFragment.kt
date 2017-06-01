package com.bearcave.passageplanning.routes


import android.content.Intent
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.editor.RouteEditorActivity
import com.bearcave.passageplanning.waypoints.database.Waypoint
import java.util.*


class RouteManagerFragment : BaseManagerFragment<Route, Int>(), RouteCRUD, ReadWaypoints {


    override var adapter: BaseManagerAdapter<Route, Int>
        get() = adapter
        set(value) { adapter = value }
    override val databaseKey: Int
        get() = RouteCRUD.ID

    private var waypointsHolder: ReadWaypoints? = null

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

    override fun onDataCreated(result: Route) {
        insert(result)
        adapter.add(result)
    }

    override fun onDataUpdated(result: Route) {
        database!!.update(result)
        adapter.update(result)
    }

    override fun createAdapter(): BaseManagerAdapter<Route, Int> = RouteManagerAdapter(this, context)


    override fun readAllWaypoints(): List<Waypoint> {
        return waypointsHolder!!.readAllWaypoints()
    }
}// Required empty public constructor
