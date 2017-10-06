package com.bearcave.passageplanning.settings

import android.content.Context
import android.support.v7.preference.PreferenceManager
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.tides.web.configurationitems.DownloadingConfiguration
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays
import org.joda.time.DateTime


/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class Settings {

    companion object {
        fun getDownloadingConfiguration(gauge: Gauge) = DownloadingConfiguration(
                gauge,
                DateTime.now(),
                NumberOfDays.WEEK,
                MinuteStep.ONE
        )


        private fun getManager(context: Context) = PreferenceManager
                .getDefaultSharedPreferences(context)

    }
}