package com.bearcave.passageplanning.passage_monitor


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime


class FootFragment : Fragment() {

    private var toGoView: TextView? = null
    private var courseView: TextView? = null
    private var etaView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_foot, container, false)

        val waypoint = arguments.getParcelable<Waypoint>(WAYPOINT_KEY)
        ButterKnife.findById<TextView>(view, R.id.last_waypoint)
                .text = waypoint.name

        toGoView = ButterKnife.findById(view, R.id.to_go)
        courseView = ButterKnife.findById(view, R.id.course)

        etaView = ButterKnife.findById(view, R.id.eta)
        return view
    }

    fun setToGo(toGo: Float) {
        toGoView?.text = "${toGo / Settings.NAUTICAL_MILE}"
    }

    fun setCourse(course: Float) {
        courseView?.text = "$course"
    }

    fun setEta(eta: DateTime){
        etaView?.text = eta.toString("HH:mm")
    }

    companion object {
        val WAYPOINT_KEY = "last_waypoint"
    }
}
