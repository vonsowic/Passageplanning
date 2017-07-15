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
import com.bearcave.passageplanning.tides.database.TideComparator
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TideManagerAdapter(val parent: TidesManagerFragment)
    : BaseAdapter() {

    var allTides =  ArrayList<TideItem>()
    var tides = allTides    // all tides with filterDate

    init {
        reload()
    }

    var dateFilter = Companion::TODAY
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
                .filter { dateFilter(it) }
                .filter { filterStep(it) }
                as ArrayList<TideItem>
    }

    private fun filterStep(item: TideItem) = item.id.minuteOfHour % stepFilter.value == 0

    fun filterOnlyTides() {
        val demandedIndexes = HashSet<Int>()

        val heights = allTides
                .map { it.predictedTideHeight }
                .withIndex()
                .toList()

        var first = 0
        var last = 0

        var isFirstSmallerThan = false
        var isFirstBiggerThan = false

        var isLastSmallerThanFirst = false
        var isLastBiggerThanFirst  = false

        for ((index, height) in heights){
            if( !(isFirstBiggerThan || isFirstSmallerThan)) {
                isFirstSmallerThan = height > heights[first].value
                isFirstBiggerThan = height < heights[first].value

                if( isFirstBiggerThan || isFirstSmallerThan){
                    first = index
                }
            } else {
                isLastSmallerThanFirst = height < heights[first].value
                isLastBiggerThanFirst  = height > heights[first].value

                if( isLastBiggerThanFirst || isLastSmallerThanFirst){
                    last = index

                    if ((isFirstBiggerThan && isLastSmallerThanFirst) || (isFirstSmallerThan && isLastBiggerThanFirst)){  // the point that was looked for is found
                        demandedIndexes.add((first + last) / 2)
                    }

                    first = index
                    isFirstBiggerThan = isLastBiggerThanFirst
                    isFirstSmallerThan= isLastSmallerThanFirst
                    isLastSmallerThanFirst = false
                    isLastBiggerThanFirst  = false

                }
            }
        }

        tides = allTides
                .filterIndexed { index, _ -> demandedIndexes.contains(index) }
                as ArrayList<TideItem>

        notifyDataSetChanged()
    }


    fun doInBackground(vararg tasks: ()->Unit) {
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

    companion object {
        fun TODAY(i: TideItem): Boolean {
            val today = DateTime.now()
            return today.dayOfMonth == i.id.dayOfMonth
                    && today.monthOfYear == i.id.monthOfYear
        }


        fun TOMORROW(i: TideItem): Boolean {
            val tomorrow = DateTime.now().plusDays(1)
            return tomorrow.dayOfMonth == i.id.dayOfMonth && tomorrow.monthOfYear == i.id.monthOfYear
        }

        fun WEEK(i: TideItem): Boolean {
            val today = DateTime.now()
            val dayNextWeek = DateTime.now().plusDays(6)
            return (i.id.dayOfMonth >= today.dayOfMonth && i.id.dayOfMonth <= dayNextWeek.dayOfMonth)
                    && (today.monthOfYear == i.id.monthOfYear || dayNextWeek.monthOfYear == i.id.monthOfYear )
        }

        fun ALL(i: TideItem): Boolean = true
    }

    inner class AdapterTask(context: Context) : BackgroundTask(context){
        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
            notifyDataSetChanged()
        }
    }
}
