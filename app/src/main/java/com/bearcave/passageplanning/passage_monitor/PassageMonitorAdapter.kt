package com.bearcave.passageplanning.passage_monitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint

/**
 *
 * @author Michał Wąsowicz
 * @since 17.06.17
 * @version 1.0
 */
class PassageMonitorAdapter(val context: Context, val passage: Passage) : BaseAdapter(){

    private val inflater
        get() = LayoutInflater.from(context)

    val waypoints: ArrayList<Waypoint> = (context as ReadWaypoints)
            .readWith(passage.route.waypointsIds) as ArrayList<Waypoint>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_item, parent, false)
        val wpt = waypoints[position]
        ButterKnife.findById<TextView>(view, R.id.waypoint)
                .text = wpt.name
        ButterKnife.findById<TextView>(view, R.id.ukc)
                .text = wpt.ukc.toString()

        return view
    }

    override fun getItem(position: Int) = waypoints[position]

    override fun getItemId(position: Int): Long = passage.route.waypointsIds[position].toLong()

    override fun getCount(): Int = passage.route.waypointsIds.size
}