package com.bearcave.passageplanning.tides.web.configurationitems

import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 30.05.17
 * @version 1.0
 */
data class DownloadingConfiguration(
        val gauge: Gauge,
        val dateTime: DateTime = DateTime.now(),
        val numberOfDays: NumberOfDays = NumberOfDays.MONTH,
        val step: MinuteStep = MinuteStep.TEN,
        val fileType: FileType = FileType.XML
)