package com.bearcave.passageplanning.passage_monitor


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.ButterKnife

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint


class PassageMonitorFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_passage_monitor, container, false)

        val waypointsView = ButterKnife.findById<ListView>(view, R.id.list)
        val adapter = PassageMonitorAdapter(context, arguments.getParcelable(PASSAGE_KEY))
        waypointsView.adapter = adapter

        val ftransaction = activity.supportFragmentManager.beginTransaction()
        val footFragment = FootFragment()
        val footBundle = Bundle()
        footBundle.putParcelable(FootFragment.WAYPOINT_KEY, adapter.waypoints.lastWaypoint)
        footFragment.arguments = footBundle
        ftransaction.replace(R.id.last_waypoint, footFragment)
        ftransaction.commit()

        return view
    }

    companion object {
        val PASSAGE_KEY = "passage key"
    }
}
