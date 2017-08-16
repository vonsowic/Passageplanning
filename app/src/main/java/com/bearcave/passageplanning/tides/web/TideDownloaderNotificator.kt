package com.bearcave.passageplanning.tides.web

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.NotificationCompat
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge

/**
 *
 * @author Michał Wąsowicz
 * @since 16.08.17
 * @version 1.0
 */
class TideDownloaderNotificator(context: Context) {

    private val mNotifyManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val mBuilder: android.support.v4.app.NotificationCompat.Builder
    private val id = 0

    private val broadcastManager = LocalBroadcastManager.getInstance(context)

    private val onProgressUpdatedReceiver: BroadcastReceiver
    private val onTaskFinishedReceiver: BroadcastReceiver

    init {
        mBuilder = NotificationCompat.Builder(context)
        mBuilder.setContentTitle("Updating tides database")
                .setContentText("It may take a few minutes…")
                .setSmallIcon(R.drawable.ic_file_download_black_24dp)

        onProgressUpdatedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent
                        ?.getStringExtra(TideManagerService.UPDATED)!!
                        .split("/")[0]
                        .toInt()

                mBuilder.setProgress(Gauge.values().size, status, false)
                mNotifyManager.notify(id, mBuilder.build())
            }
        }

        onTaskFinishedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                mBuilder.setContentText("Download complete")
                        .setProgress(0,0,false)

                mNotifyManager.notify(id, mBuilder.build())
                dismiss()
            }
        }

        register()
    }

    private fun register(){
        broadcastManager.registerReceiver(onProgressUpdatedReceiver, IntentFilter(TideManagerService.UPDATED))
        broadcastManager.registerReceiver(onTaskFinishedReceiver, IntentFilter(TideManagerService.FINISHED))
    }

    fun show(){
        mBuilder.setProgress(Gauge.values().size, 0, false)
        mNotifyManager.notify(id, mBuilder.build())
    }

    private fun dismiss(){
        broadcastManager.unregisterReceiver(onProgressUpdatedReceiver)
        broadcastManager.unregisterReceiver(onTaskFinishedReceiver)
    }
}