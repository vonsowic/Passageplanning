package com.bearcave.passageplanning.tasks

import android.content.Context
import android.os.AsyncTask

/**
 *
 * @author Michał Wąsowicz
 * @since 03.06.17
 * @version 1.0
 */
abstract class InternetTask<Params, Progress, Result>(val context: Context) : AsyncTask<Params, Progress, Result>()