package com.bearcave.passageplanning.passage_monitor.passage_list_adapter

import android.content.Context
import android.graphics.Color
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
import com.bearcave.passageplanning.passage_monitor.PassageMonitorFragment
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.passages.planner.PassagePlan
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.utils.round
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 17.06.17
 * @version 1.0
 */
class PassageMonitorAdapter(val parent: PassageMonitorFragment, val waypoints: PassagePlan) : BaseAdapter(){

    private val inflater
        get() = LayoutInflater.from(context)

    private val context: Context
        get() = parent.context

    private val listener = context as PassageMonitor

    private val passage: Passage
        get() = waypoints.passage


    var selected = 0
        private set


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_item, parent, false)
        val wpt = waypoints[position]

        ButterKnife.findById<TextView>(view, R.id.waypoint)
                .text = wpt.name

        ButterKnife.findById<TextView>(view, R.id.cd)
                .text = round(wpt.ukc).toString()

        ButterKnife.findById<TextView>(view, R.id.ukc)
                .text = round(waypoints.ukc(position)).toString()

        ButterKnife.findById<TextView>(view, R.id.togo)
                 .text = if (position >= selected) (round(waypoints.toGo(position) / Settings.NAUTICAL_MILE, 1)).toString() else "--"

        ButterKnife.findById<TextView>(view, R.id.bearing)
                .text = if (position >= selected) waypoints.course(position).toInt().toString() else "--"

        ButterKnife.findById<TextView>(view, R.id.eta_text)
                .text = if (position >= selected) waypoints.eta(position).toString("HH:mm") else "--"

        ButterKnife.findById<TextView>(view, R.id.dist)
                .text = (round(waypoints.dist(position) / Settings.NAUTICAL_MILE, 1)).toString()

        ButterKnife.findById<ImageView>(view, R.id.options_button)
                .setOnClickListener { showPopupMenu(it, wpt) }

        view.setOnClickListener {
            selected = position
            selectWaypoint()
            notifyDataSetChanged()
        }

        if (position == selected)
            view.setBackgroundColor(Color.LTGRAY)
        else
            view.setBackgroundColor(Color.WHITE)


        return view
    }

    override fun getItem(position: Int) = waypoints[position]

    override fun getItemId(position: Int): Long = passage.route.waypointsIds[position].toLong()

    override fun getCount(): Int = passage.route.waypointsIds.size - 1

    private fun showNotes(waypoint: Waypoint) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setMessage("Characteristic: ${waypoint.characteristic}\nNotes: ${waypoint.note}")
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

    interface PassageMonitor {
        fun setToGo(toGo: Float)
        fun setCourse(course: Float)
        fun setEta(eta: DateTime)
    }

    fun selectWaypoint() {
        listener.setToGo(waypoints.toGo(selected))
        listener.setCourse(waypoints.course(selected))
        listener.setEta(waypoints.etaAtEnd())
    }
}