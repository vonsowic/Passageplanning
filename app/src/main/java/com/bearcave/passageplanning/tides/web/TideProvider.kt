package com.bearcave.passageplanning.tides.web


import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.tides.web.configurationitems.DownloadingConfiguration
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays
import org.joda.time.DateTime
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.IOException
import java.util.*

class TideProvider {

    fun load(configuration: DownloadingConfiguration)
            = load(configuration.gauge, configuration.dateTime, configuration.numberOfDays, configuration.step)

    @Throws(IOException::class)
    fun load(gauge: Gauge, time: DateTime, numberOfDays: NumberOfDays, step: MinuteStep): HashSet<TideItem> {
        val data = get(gauge, time, numberOfDays, step).getElementsByTag("item")
        return data.mapTo(HashSet<TideItem>()) { convertElement(it) }
    }

    private operator fun get(conf: DownloadingConfiguration) =
        get(conf.gauge, conf.dateTime, conf.numberOfDays, conf.step)

    @Throws(IOException::class)
    private operator fun get(gauge: Gauge, time: DateTime, numberOfDays: NumberOfDays, step: MinuteStep): Document {
        val url = UrlBuilder(
                gauge,
                time,
                numberOfDays,
                step
        )
        return Jsoup.connect(url.build()).get()
    }

    private fun convertElement(data: Element): TideItem {
        return TideItem(
                getTimeFrom(data),
                java.lang.Float.valueOf(data.getElementsByTag("pred").first().text())!!
        )
    }

    private fun getTimeFrom(element: Element): DateTime {
        val date = element.getElementsByTag("date").first().text()
        val time = element.getElementsByTag("time").first().text()

        return DateTime(
                Integer.valueOf(date.substring(0, 4))!!,
                Integer.valueOf(date.substring(5, 7))!!,
                Integer.valueOf(date.substring(8, 10))!!,
                Integer.valueOf(time.substring(0, 2))!!,
                Integer.valueOf(time.substring(3, 5))!!
        )

    }
}
