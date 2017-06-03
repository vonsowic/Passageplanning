package com.bearcave.passageplanning.tasks

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.bearcave.passageplanning.R

/**
 *
 * @author Michał Wąsowicz
 * @since 03.06.17
 * @version 1.0
 */
abstract class InternetTaskWithMessage<Params, Progress, Result>(context: Context)
    : InternetTask<Params, Progress, Result>(context) {

    private val progressDialog = ProgressDialog(context)

    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(context, "Starting task", Toast.LENGTH_SHORT).show()
        progressDialog.setMessage(context.getString(R.string.fetching_data))
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun onPostExecute(result: Result?) {
        super.onPostExecute(result)
        progressDialog.dismiss()
    }
}