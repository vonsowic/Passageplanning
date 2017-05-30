package com.bearcave.passageplanning.thames_tide_provider

import android.os.AsyncTask
import com.bearcave.passageplanning.thames_tide_provider.database.TideCRUD
import com.bearcave.passageplanning.thames_tide_provider.database.TideItem
import com.bearcave.passageplanning.thames_tide_provider.web.TideProvider
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.DownloadingConfiguration
import java.io.IOException

/**
 *
 * @author Michał Wąsowicz
 * @since 30.05.17
 * @version 1.0
 */
class DownloadTideTableTask(val database: TideCRUD) : AsyncTask<DownloadingConfiguration, Void,  HashSet<TideItem>>() {

    val provider = TideProvider()
    var exception: Exception? = null

    override fun doInBackground(vararg params: DownloadingConfiguration?): HashSet<TideItem> {
        val configuration = params[0]
        var result = HashSet<TideItem>()
        try {
            result = provider.load(configuration!!.gauge, configuration.dateTime, configuration.numberOfDays, configuration.step)
        } catch (e: IOException){
            exception = e
        } finally {
            return result
        }
    }

    override fun onPostExecute(result: HashSet<TideItem>?) {
        super.onPostExecute(result)

    }
}