package com.bearcave.passageplanning.settings

import android.content.Context
import com.bearcave.passageplanning.tides.web.configurationitems.DownloadingConfiguration
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays
import org.joda.time.DateTime
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager


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
                MinuteStep.TEN
        )

        val LENGTH_KEY = "pref_length_unit_type"
        val SPEED_KEY = "pref_speed_unit_type"

        val metricMap = hashMapOf(
                Pair("m", 1F),
                Pair("Mm", 1852F),
                Pair("km", 1000F),
                Pair("m/s", 1F),
                Pair("km/s", 3.6F),
                Pair("kts", 1.9438F),
                Pair("mph", 2.2369F)
        )

        fun lengthValue(context: Context) = metricMap[lengthUnit(context)]!!

        fun lengthUnit(context: Context) = getManager(context)
                .getString(LENGTH_KEY, "Mm")!!

        fun speedValue(context: Context) = metricMap[speedUnit(context)]!!

        fun speedUnit(context: Context) = getManager(context)
                .getString(SPEED_KEY, "kts")!!

        private fun getManager(context: Context) = PreferenceManager
                .getDefaultSharedPreferences(context)
    }
}