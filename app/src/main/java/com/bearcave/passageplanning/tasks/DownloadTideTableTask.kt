package com.bearcave.passageplanning.tasks

import android.content.Context
import android.util.Log
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.tides.database.TideCRUD
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.web.TideProvider
import com.bearcave.passageplanning.tides.web.configurationitems.DownloadingConfiguration

/**
 *
 * @author Michał Wąsowicz
 * @since 30.05.17
 * @version 1.0
 */
class DownloadTideTableTask(context: Context) : InternetTask<DownloadingConfiguration, Void, HashSet<TideItem>>(context) {

    val provider = TideProvider()
    var database: TideCRUD? = null

    private val LOG_MESSAGE = "DOWNLOADING TIDE TASK: "

    override fun doInBackground(vararg params: DownloadingConfiguration?): HashSet<TideItem> {
        val configuration = params[0]!!
        database = (context as OnDatabaseRequestedListener)
                .onGetTableListener(configuration.gauge.id) as TideCRUD

        return provider.load(
                configuration.gauge,
                configuration.dateTime,
                configuration.numberOfDays,
                configuration.step
        )
    }


    override fun onPostExecute(result: HashSet<TideItem>?) {
        super.onPostExecute(result)
        Log.e(LOG_MESSAGE, "got ${result!!.size} items")
        database!!.insertAll(result)
    }
}