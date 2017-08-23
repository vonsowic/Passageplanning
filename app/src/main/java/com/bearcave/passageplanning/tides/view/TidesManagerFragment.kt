package com.bearcave.passageplanning.tides.view

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseFragment
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.tasks.TaskUpdaterListener
import com.bearcave.passageplanning.tides.database.DateFilter
import com.bearcave.passageplanning.tides.database.TideCRUD
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.tides.web.TideManagerService
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.1
 */
class TidesManagerFragment : BaseFragment(), TideCRUD, TaskUpdaterListener {

    private var progressDialog: ProgressDialog? = null

    private var downloader: Intent? = null

    private val onTaskFinishedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onTaskFinished()
        }
    }

    private val onProgressUpdatedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onTaskUpdated(intent!!.getStringExtra(TideManagerService.UPDATED).toInt())
        }
    }

    private val onNoInternetConnectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onNoInternetConnection()
        }
    }

    private var selectedGauge = Gauge.MARGATE

    private var adapter: TideManagerAdapter? = null

    private val tidesTables = HashMap<Gauge, Lazy<TidesTable>>(Gauge.values().size)

    override fun layoutId() = R.layout.fragment_tides_manager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder: OnDatabaseRequestedListener = context as OnDatabaseRequestedListener
        for (gauge in Gauge.values()){
            tidesTables[gauge] = lazy { databaseHolder.onGetTableListener(gauge.id) as TidesTable }
        }

        progressDialog = ProgressDialog(context)
        progressDialog!!.isIndeterminate = false
        progressDialog!!.setCancelable(true)
        progressDialog!!.setOnCancelListener {
            onCancelClicked()
        }
        onTaskUpdated(0)

        downloader = Intent(context, TideManagerService::class.java)
        registerOnNoConnectionReceiver()
    }

    override val title: String
        get() = selectedGauge.humanCode


    override fun findViews(parent: View) {
        super.findViews(parent)
        setHasOptionsMenu(true)

        adapter = TideManagerAdapter(this)
        ButterKnife.findById<ListView>(parent, R.id.list_view)
                .adapter = adapter

        ButterKnife.findById<FloatingActionButton>(parent, R.id.update_button)
                .setOnClickListener { updateTidesDatabase() }
    }


    override fun onCreateOptionsMenu(optionsMenu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(optionsMenu, inflater)
        inflater?.inflate(R.menu.tide_manager_menu, optionsMenu)

        // add all gauges to menu
        for (gauge in tidesTables.keys){
            optionsMenu?.add(Menu.NONE, gauge.id, Menu.NONE, gauge.humanCode)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ) {
            R.id.filter_date -> showFilterDateMenu(ButterKnife.findById(activity, R.id.filter_date))
            R.id.filter_step -> showFilterStepMenu(ButterKnife.findById(activity, R.id.filter_step))
            R.id.filter_tides-> adapter!!.showOnlyTides()
            else -> {
               selectedGauge = Gauge.getById(item?.itemId!!)
               adapter!!.reload()
               activity.title = title
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showFilterDateMenu(anchor: View){
        val menu = PopupMenu(context, anchor)
        menu.inflate(R.menu.tide_filter_date_menu)

        menu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.today ->       adapter!!.dateFilter = DateFilter.Companion::TODAY
                R.id.tomorrow ->    adapter!!.dateFilter = DateFilter.Companion::TOMORROW
                R.id.week ->        adapter!!.dateFilter = DateFilter.Companion::WEEK
                R.id.all ->         adapter!!.dateFilter = DateFilter.Companion::ALL
            }
            true
        }

        menu.show()
    }


    private fun showFilterStepMenu(anchor: View){
        val menu = PopupMenu(context, anchor)
        menu.inflate(R.menu.tide_filter_step_menu)

        for (step in MinuteStep.values()){
            menu.menu.add(Menu.NONE, step.value, Menu.NONE, "${step.value} minutes")
        }

        menu.setOnMenuItemClickListener { item ->
            adapter!!.stepFilter = MinuteStep.getByValue(item.itemId)
            true
        }

        menu.show()
    }

    // run downloading service
    private fun updateTidesDatabase() {
        registerOnProgressUpdatedReceiver()
        registerOnTaskFinishedReceiver()

        if( !isDownloadingRunning() ){
            context.startService(downloader)
        } else {
            TideManagerService.instance?.onTaskUpdated(0)
        }

        showProgressDialog()
    }

    override fun insert(element: TideItem) = tidesTables[selectedGauge]!!.value.insert(element)

    override fun insertAll(tides: Collection<TideItem>) = tidesTables[selectedGauge]!!.value.insertAll(tides)

    override fun read(id: DateTime) = tidesTables[selectedGauge]!!.value.read(id)

    override fun readAll() = tidesTables[selectedGauge]!!.value.readAll()

    override fun update(element: TideItem)  = tidesTables[selectedGauge]!!.value.update(element)

    override fun delete(element: TideItem) = tidesTables[selectedGauge]!!.value.delete(element)


    private fun showProgressDialog() {
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.hide()
    }

    private fun register(receiver: BroadcastReceiver, filter: String) {
        LocalBroadcastManager
                .getInstance(context)
                .registerReceiver(receiver, IntentFilter(filter))
    }

    private fun unregister(receiver: BroadcastReceiver) {
        LocalBroadcastManager
                .getInstance(context)
                .unregisterReceiver(receiver)
    }

    private fun registerOnNoConnectionReceiver() {
        register(onNoInternetConnectionReceiver, TideManagerService.NO_INTERNET_CONNECTION)
    }

    private fun unregisterOnNoConnectionReceiver() {
        unregister(onNoInternetConnectionReceiver)
    }

    private fun registerOnTaskFinishedReceiver() {
        register(onTaskFinishedReceiver, TideManagerService.FINISHED)
    }

    private fun unregisterOnTaskFinishedReceiver() {
        unregister(onTaskFinishedReceiver)
    }

    private fun registerOnProgressUpdatedReceiver() {
        register(onProgressUpdatedReceiver, TideManagerService.UPDATED)
    }

    private fun unregisterOnProgressUpdatedReceiver() {
        unregister(onProgressUpdatedReceiver)
    }

    private fun isDownloadingRunning() = TideManagerService.instance?.isRunning ?: false

    override fun onNoInternetConnection() {
        hideProgressDialog()
        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
    }

    override fun onTaskFinished() {
        adapter!!.reload()
        progressDialog?.dismiss()
        unregisterOnTaskFinishedReceiver()
        unregisterOnProgressUpdatedReceiver()
    }

    override fun onTaskUpdated(progress: Int) {
        progressDialog?.setMessage("${context.getString(R.string.downloading_data)}\nProgress: $progress/${Gauge.values().size}")
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null

        unregisterOnNoConnectionReceiver()
        unregisterOnProgressUpdatedReceiver()
        unregisterOnTaskFinishedReceiver()
    }


    private fun onCancelClicked() {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Abort?")
        alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                "Yes",
                {
                    _, _ ->
                    run {
                        LocalBroadcastManager
                                .getInstance(context)
                                .sendBroadcast(
                                        Intent(TideManagerService.CANCELED)
                                )
                    }
                }
        )

        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                "Continue in background",
                { _, _ ->
                    run {
                        hideProgressDialog()
                        unregisterOnProgressUpdatedReceiver()
                    }
                }
        )

        alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                "Cancel",
                {
                    _, _ -> run{
                    showProgressDialog()
                    }
                }
        )

        alertDialog.show()
    }
}