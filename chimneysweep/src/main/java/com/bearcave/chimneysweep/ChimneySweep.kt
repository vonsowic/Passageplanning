package com.bearcave.chimneysweep

import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 01.10.17
 */
open class ChimneySweep : AsyncTask<() -> Unit, Void, Void>() {

    var progressWindow: ProgressBar? = null
        private set

    fun setProgressWindow(window: ProgressBar): ChimneySweep{
        this.progressWindow = window
        return this
    }

    override fun onPreExecute() {
        super.onPreExecute()
        progressWindow?.visibility = View.VISIBLE
    }

    override fun doInBackground(vararg params: () -> Unit): Void? {
        params.forEach { it() }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        progressWindow?.visibility = View.GONE
    }
}