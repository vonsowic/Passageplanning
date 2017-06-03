package com.bearcave.passageplanning.tasks

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.bearcave.passageplanning.MainActivity

/**
 *
 * @author Michał Wąsowicz
 * @since 03.06.17
 * @version 1.0
 */
abstract class InternetTask<Params, Progress, Result>(val context: Context)
    : AsyncTask<Params, Progress, Result>() {

    override fun onPreExecute() {
        super.onPreExecute()
        /*
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.INTERNET),
                    MainActivity.PASSAGE_PERMISSIONS_REQUEST_INTERNET
            )
        }
        */
    }
}