package com.bearcave.passageplanning.tasks

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.Fragment
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge


/**
 *
 * @author Michał Wąsowicz
 * @since 13.07.17
 * @version 1.0
 */
class UpdateTideTablesTask(val parent: Fragment) : AsyncTask<Gauge, Void, Int>() {


    private val listener = parent as TaskUpdaterListener

    val context: Context
        get() = parent.context

    private var lastTask: Thread? = null

    private var noInternetConnection = false


    override fun doInBackground(vararg params: Gauge): Int {


        return 0
    }


    override fun onPostExecute(result: Int) {
        super.onPostExecute(result)
        //progressDialog.dismiss()

        if(noInternetConnection){
            listener.onNoInternetConnection()
            return
        }

        if(result == 0)
            listener.onTaskFinished()
    }


}