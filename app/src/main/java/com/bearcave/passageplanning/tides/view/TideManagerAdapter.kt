package com.bearcave.passageplanning.tides.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.tasks.BackgroundTask
import com.bearcave.passageplanning.tides.database.*
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TideManagerAdapter(val parent: TidesManagerFragment)
    : BaseAdapter() {

    var allTides = ArrayList<TideItem>()
    var tides = allTides    // all tides with filterDate

    init {
        reload()
    }

    var dateFilter = DateFilter.Companion::TODAY
        set(value) {
            field = value
            filter()
        }

    var stepFilter = MinuteStep.TEN
        set(value) {
            field = value
            filter()
        }

    val context: Context
        get() = parent.context

    val inflater: LayoutInflater
        get() = LayoutInflater.from(context)


    fun showOnlyTides(){
        doInBackground({
            tides = allTides.filterOnlyTides() as ArrayList<TideItem>
        })
    }


    fun reload() {
        doInBackground(
                this::reloadTask,
                this::filterTask
        )
    }


    private fun reloadTask(){
        allTides = parent.readAll() as ArrayList<TideItem>
        allTides.sortWith(TideComparator())
        tides = allTides
    }


    private fun filter() {
        doInBackground(this::filterTask)
    }


    private fun filterTask(){
        tides = allTides
                .filterByDate(dateFilter)
                .filterByStep(stepFilter.value)
        as ArrayList<TideItem>
    }


    private fun doInBackground(vararg tasks: ()->Unit) {
        AdapterTask(context).execute(*tasks)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.tide_item, parent, false)

        ButterKnife.findById<TextView>(view, R.id.time)
                .text = tides[position].id.toString("dd.MM.YY - HH:mm")

        ButterKnife.findById<TextView>(view, R.id.tide_height)
                .text = tides[position].predictedTideHeight.toString()

        return view
    }

    override fun getItem(position: Int) = tides[ position ]

    override fun getItemId(position: Int): Long = 0

    override fun getCount() = tides.size



    inner class AdapterTask(context: Context) : BackgroundTask(context){
        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
            notifyDataSetChanged()
        }
    }
}
