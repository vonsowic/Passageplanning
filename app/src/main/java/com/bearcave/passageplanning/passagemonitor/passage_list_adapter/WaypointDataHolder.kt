package com.bearcave.passageplanning.passagemonitor.passage_list_adapter

import com.bearcave.passageplanning.App.Companion.context
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.tides.database.TideNotInDatabaseException
import com.bearcave.passageplanning.utils.convertFromMsToKts
import com.bearcave.passageplanning.utils.round
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 24.10.17
 * @version 1.0
 */
data class WaypointDataHolder(
        val cd: String,
        val ukc: String,
        val speed: String,
        val togo: String,
        val eta: String
){
    constructor(
            cd: Float,
            ukc: Float,
            speed: Float,
            togo: String,
            eta: DateTime
    ): this(
            // cd
            round(cd).toString(),
            // ukc
            try{ round(ukc).toString() }
            catch (_: TideNotInDatabaseException){ context.getString(R.string.tide_height_not_available) },
            // speed
            try { (round(speed.convertFromMsToKts(), 1)).toString() }
            catch (_: TideNotInDatabaseException){ context.getString(R.string.tide_height_not_available)},
            // to go
            togo,
            // ETA
            eta.toString("HH:mm")
    )
}