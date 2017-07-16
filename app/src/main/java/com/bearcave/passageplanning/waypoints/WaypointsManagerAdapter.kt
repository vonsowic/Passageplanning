package com.bearcave.passageplanning.waypoints

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.waypoints.database.Waypoint


class WaypointsManagerAdapter(parent: WaypointsManagerFragment, context: Context) : BaseManagerAdapter<Waypoint, Int>(parent, context) {

    init {
        addOption(context.getString(R.string.action_edit), { dao -> parent.openEditor(dao)})
    }

    override fun getChildrenCount(groupPosition: Int): Int = 1

    override fun getGroupId(groupPosition: Int): Long = container[groupPosition].id.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = 0

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.waypoint_child_item, parent, false)

        val wpt = container[groupPosition]

        // note
        ButterKnife.findById<TextView>(view!!, R.id.note)
            .text = "Note ${wpt.note}"

        // characteristic
        ButterKnife.findById<TextView>(view, R.id.characteristic)
            .text = "Characteristic: ${wpt.characteristic}"

        // gauge
        ButterKnife.findById<TextView>(view, R.id.gauge)
            .text = "Gauge: ${wpt.gauge.humanCode}"

        // ukc
        ButterKnife.findById<TextView>(view, R.id.ukc)
            .text = "${context.getString(R.string.ukc)} ${wpt.ukc}"

        // latitude
        ButterKnife.findById<TextView>(view, R.id.latitude)
            .text = wpt.latitudeInSecondFormat

        // longitude
        ButterKnife.findById<TextView>(view, R.id.longitude)
                .text = wpt.longitudeInSecondFormat

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false
}

