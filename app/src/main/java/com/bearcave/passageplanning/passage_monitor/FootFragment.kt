package com.bearcave.passageplanning.passage_monitor


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.waypoints.database.Waypoint


class FootFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_foot, container, false)

        val waypoint = arguments.getParcelable<Waypoint>(WAYPOINT_KEY)
        ButterKnife.findById<TextView>(view, R.id.last_waypoint)
                .text = waypoint.name

        return view
    }

    companion object {
        val WAYPOINT_KEY = "last_waypoint"
    }
}
