package com.bearcave.passageplanning.routes.editor

import android.content.Intent
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.waypoints.database.Waypoint
import kotlinx.android.synthetic.main.content_route_editor.*
import java.util.*


class RouteEditorActivity : BaseEditorActivity<Route>(), RouteEditorAdapter.OnItemClickedListener {


    private var chosenWaypoints = ArrayList<Int>()
    private var waypoints = ArrayList<Waypoint>()

    private var routeId: Int = -2

    override fun getParcelableExtra(intent: Intent) {
        waypoints = intent.getParcelableArrayListExtra<Waypoint>(WAYPOINTS_KEY) ?: waypoints
    }

    override fun findViews() {
        super.findViews()
        waypointsChooser.adapter = RouteEditorAdapter(this, waypoints)
    }

    override fun setViewsContent(`object`: Route) {
        routeId = `object`.id
        name!!.setText(`object`.name)
        chosenWaypoints = `object`.waypointsIds
    }

    override val contentLayoutId: Int
        get() = R.layout.content_route_editor

    override val isAllFilled: Boolean
        get() = name!!.text.isNotEmpty() && chosenWaypoints.size != 0

    override val filledDAO: Route
        get() = Route(
                routeId,
                name!!.text.toString(),
                chosenWaypoints
        )

    override fun isItemCheckedListener(id: Int): Boolean = chosenWaypoints.contains(id)

    override fun onItemCheckListener(id: Int) {
        if (chosenWaypoints.contains(id)) {
            chosenWaypoints.remove(id)
        } else
            chosenWaypoints.add(id)
    }

    companion object {
        val WAYPOINTS_KEY = "waypoints_from_database"
    }
}
