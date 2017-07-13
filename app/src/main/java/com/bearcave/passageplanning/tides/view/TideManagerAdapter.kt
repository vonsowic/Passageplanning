package com.bearcave.passageplanning.tides.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.tides.database.TideComparator
import com.bearcave.passageplanning.tides.database.TideItem

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TideManagerAdapter(parent: TidesManagerFragment, val context: Context)
    : BaseAdapter() {

    val tides = parent.readAll() as ArrayList<TideItem>

    init {
        tides.sortWith(TideComparator())
    }

    val inflater
        get() = android.view.LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.tide_item, parent, false)

        ButterKnife.findById<TextView>(view, R.id.time)
                .text = tides[position].id.toString("dd.MM.YY - HH:mm")

        ButterKnife.findById<TextView>(view, R.id.tide_height)
                .text = tides[position].predictedTideHeight.toString()

        return view
    }

    override fun getItem(position: Int) = tides[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount() = tides.size
}
