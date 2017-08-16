package com.bearcave.passageplanning.routes.editor

import android.content.Intent
import android.widget.EditText
import android.widget.ListView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.waypoints.database.Waypoint
import java.util.*


class RouteEditorActivity : BaseEditorActivity<Route>(), RouteEditorAdapter.OnItemClickedListener {

    private var name: EditText? = null
    private var waypointChooser: ListView? = null

    private var chosenWaypoints = ArrayList<Int>()
    private var waypoints = ArrayList<Waypoint>()

    private var routeId: Int = -2

    override fun getParcelableExtra(intent: Intent) {
        waypoints = intent.getParcelableArrayListExtra<Waypoint>(WAYPOINTS_KEY) ?: waypoints
    }

    override fun findViews() {
        super.findViews()
        name = ButterKnife.findById(this, R.id.name_text)
        waypointChooser = ButterKnife.findById(this, R.id.waypoints_list)
        waypointChooser!!.adapter = RouteEditorAdapter(this, waypoints)
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
