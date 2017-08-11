package com.bearcave.passageplanning.tasks

import android.app.IntentService
import android.content.Intent
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.TideProvider
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import java.io.IOException


class TideManagerService : IntentService("TideManagerService"), TaskUpdaterListener {

    var isRunning = false

    override fun onHandleIntent(intent: Intent?) {
        isRunning = true
        try {
            for ((index, gauge) in Gauge.values().withIndex()) {
                val database = getDatabase(gauge.id) as TidesTable

                onTaskUpdated(index)

                val result = TideProvider()
                        .load(Settings.getDownloadingConfiguration(gauge))

                database.insertAll(result)
            }
        } catch (e: IOException){
            onNoInternetConnection()
        } finally {
            isRunning = false
        }

        onTaskFinished()
    }

    private fun getDatabase(tableId: Int) = DatabaseManager.DATABASE_MANAGER.getTable(tableId)

    override fun onTaskUpdated(progress: Int) {
        broadcast(UPDATED, progress.toString())
    }

    override fun onTaskFinished() {
        broadcast(FINISHED)
    }

    override fun onNoInternetConnection() {
        broadcast(NO_INTERNET_CONNECTION)
    }

    private fun broadcast(action: String, message: String = "") {
        val broadcastIntent = Intent()
        //broadcastIntent.action = ResponseReceiver.ACTION_RESP
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT)
        broadcastIntent.putExtra(action, message)
        sendBroadcast(broadcastIntent)
    }

    companion object {
        @JvmField val UPDATED = "tides_updated"
        @JvmField val FINISHED = "tides_downloaded"
        @JvmField val NO_INTERNET_CONNECTION= "no_internet_connection"
    }

}
