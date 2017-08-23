package com.bearcave.passageplanning.tides.web

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.support.v4.content.LocalBroadcastManager
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tasks.TaskUpdaterListener
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.utils.Gauge
import java.io.IOException


class TideManagerService : IntentService("TideManagerService"), TaskUpdaterListener {

    private var status = -1
        private set

    val isRunning
        get() = status != -1


    private var wakeLock: PowerManager.WakeLock? = null

    private var database: DatabaseManager? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        instance = this
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager)
                .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TidesDownloadingLock")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        if(wakeLock?.isHeld == false)
            wakeLock?.acquire()

        registerOnCancelledReceiver()
        TideDownloaderNotificator(this).show()
        status = 0
        try {
            for ((index, gauge) in Gauge.values().withIndex()) {
                status = index
                val database = getDatabase(gauge.id) as TidesTable

                onTaskUpdated(0)

                val result = TideProvider()
                        .load(Settings.getDownloadingConfiguration(gauge))

                database.insertAll(result)
            }

            onTaskFinished()
        } catch (e: IOException){
            onNoInternetConnection()
        } finally {
            status = -1
        }

        Gauge.values()
                .map { getDatabase(it.id) as TidesTable }
                .forEach { it.removeExpired() }

        onTaskFinished()
    }

    private fun getDatabase(tableId: Int) = database?.getTable(tableId) ?: {
        database = DatabaseManager.DATABASE_MANAGER
        database!!.getTable(tableId)
    }()

    override fun onTaskUpdated(progress: Int) {
        broadcast(UPDATED, status.toString())
    }

    override fun onTaskFinished() {
        broadcast(FINISHED)
        unregisterOnCancelledReceiver()
    }

    override fun onNoInternetConnection() {
        broadcast(NO_INTERNET_CONNECTION)
    }

    private fun broadcast(action: String, message: String = "") {
        val broadcastIntent = Intent(action)
        broadcastIntent.putExtra(action, message)

        LocalBroadcastManager
                .getInstance(this)
                .sendBroadcast(broadcastIntent)
    }


    private val onCancelledReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            status = -1
            this@TideManagerService.stopSelf()
        }

    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (!isRunning && wakeLock?.isHeld == false)
            wakeLock?.release()
        return super.onUnbind(intent)
    }

    private fun register(receiver: BroadcastReceiver, filter: String) {
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(receiver, IntentFilter(filter))
    }

    private fun unregister(receiver: BroadcastReceiver) {
        LocalBroadcastManager
                .getInstance(this)
                .unregisterReceiver(receiver)
    }

    private fun registerOnCancelledReceiver() {
        register(onCancelledReceiver, CANCELED)
    }

    private fun unregisterOnCancelledReceiver() {
        unregister(onCancelledReceiver)
    }

    override fun onDestroy() {
        broadcast(FINISHED)
        unregisterOnCancelledReceiver()
        super.onDestroy()
    }

    companion object {
        @JvmField val UPDATED = "tides_updated"
        @JvmField val FINISHED = "tides_downloaded"
        @JvmField val NO_INTERNET_CONNECTION= "no_internet_connection"
        @JvmField val CANCELED = "downloading_tides_canceled"

        @JvmField var instance: TideManagerService? = null

    }

}
