package com.bearcave.passageplanning.tasks

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.TideProvider

import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import java.io.IOException


class TideManagerService(private val listener: TaskUpdaterListener, private val context: Context) : IntentService("TideManagerService") {

    val databaseHandler = context as OnDatabaseRequestedListener

    override fun onHandleIntent(intent: Intent?) {
        try {
            for (gauge in Gauge.values()) {
                val database = databaseHandler.onGetTableListener(gauge.id) as TidesTable

                val result = TideProvider()
                        .load(Settings.getDownloadingConfiguration(gauge))

                database.insertAll(result)
            }
        } catch (e: IOException){
            listener.onNoInternetConnection()
        }

        listener.onTaskFinished()
    }
}
