package com.bearcave.passageplanning.tasks

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Binder
import android.os.IBinder
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.TideProvider
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import kotlin.concurrent.thread


/**
 * Manages tides data by operating on database and downloading data from server.
 *
 * @author Michał Wąsowicz
 * @version 1.0
 */
class TideManagerService(val activityContext: Context): Service() {


    private val binder = TideManagerBinder()
    private val listener: TideManagerListener = activityContext as TideManagerListener
    private val gauges = HashMap<Gauge, TideManagerStatus>()
    private val provider = TideProvider()

    init {
        gauges.forEach { _, _ -> TideManagerStatus.UNCHECKED }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    inner class TideManagerBinder : Binder() {
        val service: TideManagerService
            get() = this@TideManagerService
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        execute()
        return super.onStartCommand(intent, flags, startId)
    }

    interface TideManagerListener {
        fun onNoInternetConnection()
    }

    private fun getDatabase(gauge: Gauge) = (activityContext as OnDatabaseRequestedListener)
            .onGetTableListener(gauge.id) as TidesTable

    private fun updateTidesTable(gauge: Gauge, items: HashSet<TideItem>){
        getDatabase(gauge)
                .insertAll(items)
    }

    private fun isInternetConnectionActive(): Boolean {
        val activeNetwork = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private fun onNoInternetConnection() {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (isInternetConnectionActive()) {
                    execute()
                    unregisterReceiver(this)
                }
            }
        }

        registerReceiver(receiver, filter)
        listener.onNoInternetConnection()
    }

    /**
     * @return current status of gauge
     */
    fun getStatus(gauge: Gauge) = gauges[gauge]

    /**
     * Function to be used as thread's block parameter.
     */
    fun run(gauge: Gauge){
        val conf = Settings.getDownloadingConfiguration(gauge)
        if ( gauges[gauge] == TideManagerStatus.UPDATE_NECESSARY){
            if ( isInternetConnectionActive() ){
                gauges[gauge] = TideManagerStatus.IN_PROGRESS
                updateTidesTable(gauge, provider.load(conf))
                gauges[gauge] = TideManagerStatus.UP_TO_DATE
            } else {
                onNoInternetConnection()
            }
        }
    }


    fun execute(){
        gauges.forEach { gauge, _ -> thread(block = { run(gauge) } ) }
    }
}
