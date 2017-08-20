package com.bearcave.passageplanning.tasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import com.bearcave.passageplanning.R

/**
 *
 * @author Michał Wąsowicz
 * @since 14.07.17
 * @version 1.0
 */
open class BackgroundTask(val context: Context) : AsyncTask<() -> Unit, Void, Int>() {

    private val progress = ProgressDialog(context)

    init {
        progress.isIndeterminate = false
        progress.setMessage(context.getString(R.string.fetching_data))
        progress.setCancelable(true)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progress.show()
    }

    override fun doInBackground(vararg params: () -> Unit): Int {
        params.forEach { it() }
        return 0
    }

    override fun onPostExecute(result: Int) {
        super.onPostExecute(result)
        progress.dismiss()
    }
}