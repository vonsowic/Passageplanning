package com.bearcave.passageplanning.passagemonitor


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.ButterKnife

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passagemonitor.passage_list_adapter.PassageMonitorAdapter
import com.bearcave.passageplanning.passages.planner.PassagePlan
import com.bearcave.passageplanning.passages.planner.PlanGetter


class PassageMonitorFragment : Fragment(), FootFragment.FootListener {

    var adapter: PassageMonitorAdapter? = null
    var plan: PassagePlan? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        plan = (context as PlanGetter).getPlan()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_passage_monitor, container, false)

        val waypointsView = ButterKnife.findById<ListView>(view, R.id.list)
        adapter = PassageMonitorAdapter(this, plan!!)
        waypointsView.adapter = adapter

        return view
    }

    override fun onSpeedChangedLister(speed: Float) {
        plan?.passage?.speed = speed
        adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        adapter?.selectWaypoint()
    }

    val passagePlan
        get() = adapter!!.waypoints

    override fun onDetach() {
        super.onDetach()
        adapter = null
    }
}
