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
import com.bearcave.passageplanning.passage_monitor.passage_list_adapter.PassageMonitorAdapter
import com.bearcave.passageplanning.passages.planner.PassagePlan
import com.bearcave.passageplanning.passages.planner.PlanGetter


class PassageMonitorFragment : Fragment() {


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
