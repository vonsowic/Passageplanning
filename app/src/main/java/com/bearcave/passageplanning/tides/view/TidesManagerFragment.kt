package com.bearcave.passageplanning.tides.view

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BasePoorManagerFragment
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TidesManagerFragment : BasePoorManagerFragment<TideItem, DateTime>() {

    var databaseHolder: OnDatabaseRequestedListener? = null
    var chosenGauge: Gauge = Gauge.getById(databaseKey)

    override fun saveDatabaseHolder(holder: OnDatabaseRequestedListener){
        databaseHolder = holder
    }

    override val title: Int
        get() = R.string.app_name

    override val databaseKey: Int
        get() = Gauge.MARGATE.id

    override fun createAdapter(): BaseManagerAdapter<TideItem, DateTime> = TideManagerAdapter(this, context)
}