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

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getGroupId(groupPosition: Int): Long {
        return container[groupPosition].id.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.waypoint_child_item, parent, false)

        val (_, _, note1, characteristic1, ukc1, latitude, longitude, gauge1) = container[groupPosition]

        val note = ButterKnife.findById<TextView>(view!!, R.id.note)
        note.append(note1)

        val characteristic = ButterKnife.findById<TextView>(view, R.id.characteristic)
        characteristic.append(characteristic1)

        val gauge = ButterKnife.findById<TextView>(view, R.id.gauge)
        gauge.append(gauge1.humanCode)

        val ukc = ButterKnife.findById<TextView>(view, R.id.ukc)
        ukc.append(ukc1.toString())

        val position = ButterKnife.findById<TextView>(view, R.id.position)
        position.text = "$longitude\n$latitude"

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

}

