package com.bearcave.passageplanning.routes


import android.content.Context
import android.content.Intent
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.editor.RouteEditorActivity
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint
import java.util.*


class RouteManagerFragment : BaseManagerFragment<Route, Int>(), RouteCRUD, ReadWaypoints {


    override val databaseKey: Int
        get() = RouteCRUD.ID

    private var waypointsHolder: ReadWaypoints? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        waypointsHolder = context as ReadWaypoints
    }

    override val title: String
        get() = getString(R.string.routes_menu)


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

    override fun readAllWaypoints() = waypointsHolder!!.readAllWaypoints()

    override fun readWith(ids: List<Int>) = waypointsHolder!!.readWith(ids)

    override fun onDataCreated(result: Route){
        adapter!!.add(
                read(
                        insert(result).toInt()
                )
        )
    }
}
