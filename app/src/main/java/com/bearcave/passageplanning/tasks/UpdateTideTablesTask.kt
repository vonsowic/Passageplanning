package com.bearcave.passageplanning.tasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.Fragment
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.settings.Settings
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.TideProvider
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import java.io.IOException

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

    private var noInternetConnection = false

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.isIndeterminate = false
        progressDialog.setMessage(context.getString(R.string.fetching_data))
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun doInBackground(vararg params: Gauge): Int {
        try {
            for (gauge in params) {
                val database = (context as OnDatabaseRequestedListener).onGetTableListener(gauge.id) as TidesTable

                val result = TideProvider()
                        .load(Settings.getDownloadingConfiguration(gauge))

                database.insertAll(result)
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
        }
    }
}