package com.bearcave.passageplanning.settings

import com.bearcave.passageplanning.tides.web.configurationitems.DownloadingConfiguration
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
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
    companion object{
        fun getDownloadingConfiguration(gauge: Gauge) = DownloadingConfiguration(
                gauge,
                DateTime.now(),
                NumberOfDays.WEEK,
                MinuteStep.TEN
        )
    }
}