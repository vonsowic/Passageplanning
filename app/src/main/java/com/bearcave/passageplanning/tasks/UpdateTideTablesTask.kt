package com.bearcave.passageplanning.tasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.TideProvider
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import java.io.IOException
import kotlin.concurrent.thread


/**
 *
 * @author Michał Wąsowicz
 * @since 13.07.17
 * @version 1.0
 */
class UpdateTideTablesTask(val parent: Fragment) : AsyncTask<Gauge, Void, Int>() {

    private val progressDialog = ProgressDialog(context)

    private val listener = parent as TaskUpdaterListener

    val context: Context
        get() = parent.context

    private var lastTask: Thread? = null

    private var noInternetConnection = false

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.isIndeterminate = false
        progressDialog.setMessage(context.getString(R.string.fetching_data))
        progressDialog.setCancelable(true)
        progressDialog.setOnCancelListener {
            onCancelClicked()
        }
        progressDialog.show()
    }

    override fun doInBackground(vararg params: Gauge): Int {
        try {
            params.forEach {
                if (isCancelled) return -1
                val database = (context as OnDatabaseRequestedListener).onGetTableListener(it.id) as TidesTable

                val result = TideProvider()
                        .load(Settings.getDownloadingConfiguration(it))

                if (isCancelled) return -1

                lastTask?.join()
                lastTask = thread { database.insertAll(result) }
            }
        } catch (e: IOException){
            noInternetConnection = true
        }

        return 0
    }


    override fun onPostExecute(result: Int) {
        super.onPostExecute(result)
        progressDialog.dismiss()

        if(noInternetConnection){
            listener.onNoInternetConnection()
            return
        }

        if(result == 0)
            listener.onTaskFinished()
    }

    private fun onCancelClicked() {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Abort?")
        alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                "Yes",
                { dialog, _ ->
                    run {
                        cancel(true)
                        dialog.dismiss()
                    }
                }
        )

        alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                "Continue in background",
                { dialog, _ ->
                    run {
                        progressDialog.dismiss()
                        dialog.dismiss()
                    }
                }
        )

        alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                "Cancel",
                { dialog, _ -> run{
                    progressDialog.show()
                    dialog.dismiss()
                } }
        )

        alertDialog.show()
    }

    fun show() {
        progressDialog.show()
    }
}