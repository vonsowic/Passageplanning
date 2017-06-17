package com.bearcave.passageplanning.passage_monitor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage

/**
 *
 * @author Michał Wąsowicz
 * @since 17.06.17
 * @version 1.0
 */
class PassageMonitorAdapter(val context: Context, val passage: Passage) : BaseAdapter(){

    private val inflater
        get() = LayoutInflater.from(context)

    val waypoints = passage.route.waypointsIds

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_item, parent, false)

        return view
    }

    override fun getItem(position: Int): Any {
        TODO("not implemented")
    }

    override fun getItemId(position: Int): Long = passage.route.waypointsIds[position]

    override fun getCount(): Int = passage.route.waypointsIds.size
}