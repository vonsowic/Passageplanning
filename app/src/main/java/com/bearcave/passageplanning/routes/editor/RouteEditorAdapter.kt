package com.bearcave.passageplanning.routes.editor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.waypoints.database.Waypoint
import java.util.*

/**
 * Because ContextMenu hides after each click, OptionsMenu doesn't look good and list with CheckedTextView sucks.
 * @since 23.05.17
 * @author Michał Wąsowicz
 * @version 1.0
 */

class RouteEditorAdapter(context: Context, private val waypoints: ArrayList<Waypoint>) : BaseAdapter() {
    private val listener: OnItemClickedListener
    private val inflater: LayoutInflater

    init {
        this.listener = context as OnItemClickedListener
        this.inflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return waypoints.size
    }

    override fun getItem(position: Int): Any {
        return waypoints[position]
    }

    override fun getItemId(position: Int): Long {
        return waypoints[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.route_editor_list_item, parent, false)

        val (id, name) = waypoints[position]

        val title = ButterKnife.findById<TextView>(view!!, R.id.waypoint)
        title!!.text = name

        val checkbox = ButterKnife.findById<ImageView>(view, R.id.checkbox)
        setChecked(checkbox, listener.isItemCheckedListener(id))


        view.setOnClickListener {
            listener.onItemCheckListener(id)
            setChecked(checkbox, listener.isItemCheckedListener(id))
        }

        return view
    }

    private fun setChecked(view: ImageView, checked: Boolean) {
        if (checked) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    internal interface OnItemClickedListener {
        fun isItemCheckedListener(id: Int): Boolean
        fun onItemCheckListener(id: Int)
    }
}
