package com.bearcave.passageplanning.passage_monitor


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.ButterKnife

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passage_monitor.passage_list_adapter.PassageMonitorAdapter
import com.bearcave.passageplanning.passage_monitor.passage_list_adapter.PassageMonitorListener


class PassageMonitorFragment : Fragment(), PassageMonitorListener {


    var adapter: PassageMonitorAdapter? = null
    val foot = FootFragment()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_passage_monitor, container, false)

        val waypointsView = ButterKnife.findById<ListView>(view, R.id.list)
        adapter = PassageMonitorAdapter(this, arguments.getParcelable(PASSAGE_KEY))
        waypointsView.adapter = adapter

        val ftransaction = activity.supportFragmentManager.beginTransaction()
        val footBundle = Bundle()
        footBundle.putParcelable(FootFragment.WAYPOINT_KEY, adapter!!.waypoints.lastWaypoint)
        foot.arguments = footBundle
        ftransaction.replace(R.id.last_waypoint, foot)
        ftransaction.commit()

        return view
    }

    override fun onResume() {
        super.onResume()
        onWaypointSelected(adapter!!.selected)
    }

    val passagePlan
        get() = adapter!!.waypoints

    override fun onWaypointSelected(position: Int) {
        foot.setToGo(passagePlan.toGo(position))
        foot.setCourse(passagePlan.course(position))
    }

    companion object {
        val PASSAGE_KEY = "passage key"
    }
}
