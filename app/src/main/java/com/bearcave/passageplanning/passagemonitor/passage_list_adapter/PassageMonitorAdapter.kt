package com.bearcave.passageplanning.passagemonitor.passage_list_adapter

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
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
import com.bearcave.passageplanning.passagemonitor.PassageMonitorFragment
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.passages.planner.PassagePlan
import com.bearcave.passageplanning.tides.database.TideNotInDatabaseException
import com.bearcave.passageplanning.utils.convertFromMToMm
import com.bearcave.passageplanning.utils.convertFromMsToKts
import com.bearcave.passageplanning.utils.round
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 17.06.17
 * @version 1.0
 */
class PassageMonitorAdapter(val parent: PassageMonitorFragment, val waypoints: PassagePlan) : BaseAdapter(), PassagePlan.Notifier{

    private val inflater
        get() = LayoutInflater.from(context)

    private val context: Context
        get() = parent.context

    private val listener = context as PassageMonitor

    private val passage: Passage
        get() = waypoints.passage

    private val onChangeListener = parent as OnChangeStateListener

    var selected = 0
        private set

    init {
        //waypoints.notifier = this
        //callListener()
    }




    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_item, parent, false)
        val wpt = waypoints[position]


        ButterKnife.findById<TextView>(view, R.id.waypoint)
                .text = wpt.name

        // calculate in background
        WaypointGetterTask().execute( inner@{
            val waypoint = WaypointDataHolder(
                    round(waypoints.cd(position)).toString(),                                                                   // CD
                    try{ round(waypoints.ukc(position)).toString() }                                                            // UKC
                    catch (_: TideNotInDatabaseException){ context.getString(R.string.tide_height_not_available) },
                    try { (round(waypoints.speedAt(position).convertFromMsToKts(), 1)).toString() }                             // speed
                    catch (_: TideNotInDatabaseException){ context.getString(R.string.tide_height_not_available)},
                    if (position >= position) (round(waypoints.toGo(position).convertFromMToMm(), 1)).toString() else "--",     // to go

                    try { waypoints.eta(position).toString("HH:mm") }
                    catch (_: TideNotInDatabaseException){ context.getString(R.string.tide_height_not_available)}
            )

            return@inner {
                ButterKnife.findById<TextView>(view, R.id.cd)
                        .text = waypoint.cd

                ButterKnife.findById<TextView>(view, R.id.ukc)
                        .text = waypoint.ukc

                ButterKnife.findById<TextView>(view, R.id.speed)
                        .text = waypoint.speed

                ButterKnife.findById<TextView>(view, R.id.togo)
                        .text = waypoint.togo

                ButterKnife.findById<TextView>(view, R.id.eta_text)
                        .text = waypoint.eta
            }
        })


        ButterKnife.findById<TextView>(view, R.id.bearing)
                .text = if (position >= selected) waypoints.course(position).toInt().toString() else "--"


        ButterKnife.findById<TextView>(view, R.id.dist)
                .text = (round(waypoints.dist(position).convertFromMToMm(), 1)).toString()


        ButterKnife.findById<ImageView>(view, R.id.options_button)
                .setOnClickListener { showPopupMenu(it, position) }

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

    override fun getItemId(position: Int) = passage.route.waypointsIds[position].toLong()

    override fun getCount() = waypoints.size - 1 // -1 becouse the last waypoint is in the FootFragment

    private fun showNotes(position: Int) {
        val alertDialog = AlertDialog.Builder(context).create()
        val waypoint = waypoints[position]
        alertDialog.setMessage("""
            Characteristic: ${waypoint.characteristic}
            Notes: ${waypoint.note}
            Current speed: ${try{ round(waypoints.speedOfCurrent(position).convertFromMsToKts())}
                            catch (_: TideNotInDatabaseException){context.getString(R.string.tide_height_not_available)}}[kts]
            Gauge: ${waypoint.gauge.humanCode}
            Optional gauge: ${waypoint.optionalGauge.humanCode}
            Tide current station: ${waypoint.tideCurrentStation.tideCurrentStation}
        """.trimIndent())
        alertDialog.show()
    }

    private fun showPopupMenu(anchor: View, selected: Int) {
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
        fun setEta(eta: DateTime?)
    }

    fun selectWaypoint() {
        waypoints.currentWaypoint = selected
        callListener()
    }

    override fun notifyDataHasChanged() {
        notifyDataSetChanged()
        callListener()
    }

    private fun callListener(){
        WaypointGetterTask().execute(mainThread@{
            val toGo = waypoints.toGo(selected)
            val course = waypoints.course(selected)
            val eta = try{ waypoints.etaAtEnd } catch (_: TideNotInDatabaseException){ null }
            return@mainThread {
                listener.setToGo(toGo)
                listener.setCourse(course)
                listener.setEta(eta)
            }
        })

    }


    var numberOfCallers = 0
    private inner class WaypointGetterTask : AsyncTask<()->()->Unit, Void, ()->Unit>() {
        override fun onPreExecute() {
            super.onPreExecute()

            numberOfCallers++
            if(numberOfCallers == 1){
                //onChangeListener.onLoadingStarted()
            }
        }

        override fun doInBackground(vararg task: (() -> () -> Unit)?): () -> Unit = task[0]!!() // :)

        override fun onPostExecute(result: (() -> Unit)?) {
            super.onPostExecute(result)
            result?.invoke()

            numberOfCallers--
            if(numberOfCallers == 0){
                //onChangeListener.onLoadingFinished()
            }
        }
    }
}