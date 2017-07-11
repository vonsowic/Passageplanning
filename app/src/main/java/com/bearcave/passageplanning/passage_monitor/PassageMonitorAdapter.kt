package com.bearcave.passageplanning.passage_monitor

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.settings.Settings
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

    val waypoints: PassagePlan
    init {
        waypoints = (context as PlanGetter).getPlan()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_item, parent, false)
        val wpt = waypoints[position]

        ButterKnife.findById<TextView>(view, R.id.waypoint)
                .text = wpt.name

        ButterKnife.findById<TextView>(view, R.id.ukc)
                .text = wpt.ukc.toString()

        ButterKnife.findById<TextView>(view, R.id.togo)
                 .text = (waypoints.toGo(position) / 1852).toString()

        ButterKnife.findById<TextView>(view, R.id.togo_unit)
                .text = "Mm"//TODO: Settings.lengthUnit(context)

        ButterKnife.findById<TextView>(view, R.id.bearing)
                .text = waypoints.course(position).toString()

        ButterKnife.findById<TextView>(view, R.id.dist)
                .text = (waypoints.dist(position) / 1852).toString()

        ButterKnife.findById<TextView>(view, R.id.dist_unit)
                .text = "Mm"

        ButterKnife.findById<ImageView>(view, R.id.options_button)
                .setOnClickListener { showPopupMenu(it, wpt) }

        return view
    }

    override fun getItem(position: Int) = waypoints[position]

    override fun getItemId(position: Int): Long = passage.route.waypointsIds[position].toLong()

    override fun getCount(): Int = passage.route.waypointsIds.size - 1

    private fun showNotes(waypoint: Waypoint) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setMessage("Characteristic: ${waypoint.characteristic}\n\nNotes: ${waypoint.note}")
        alertDialog.show()
    }

    private fun showPopupMenu(anchor: View, selected: Waypoint) {
        val menu = PopupMenu(context, anchor)

        menu.menu.add(Menu.NONE, 0, Menu.NONE, R.string.show_notes)

        menu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                0 -> showNotes(selected)
            }
            true
        }

        menu.show()
    }
}