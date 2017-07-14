package com.bearcave.passageplanning.tides.web

import com.bearcave.passageplanning.tides.web.configurationitems.FileType
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import com.bearcave.passageplanning.tides.web.configurationitems.NumberOfDays

import org.joda.time.DateTime


class UrlBuilder(
        val gaugeCode: Gauge,
        val time: DateTime? = null,
        val numberOfDays: NumberOfDays = NumberOfDays.ONE,
        val step: MinuteStep = MinuteStep.TEN,
        val fileType: FileType = FileType.XML
) {



    fun build(): String {
        val builder = StringBuilder()
        builder.append(URL)
        builder.append(separator + gaugeCode.code)
        builder.append(separator + time!!.year)
        builder.append(separator + time.monthOfYear)
        builder.append(separator + time.dayOfMonth)
        builder.append(separator + numberOfDays.length)
        builder.append(separator + step.value)
        builder.append(separator + 0)
        builder.append(separator + fileType.typeName)

        return builder.toString()
    }

    companion object {
        val URL = "http://tidepredictions.pla.co.uk/listing_page"
        val separator = "/"
    }
}






