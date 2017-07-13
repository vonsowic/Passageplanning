package com.bearcave.passageplanning.tides.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ListView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseFragment
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.tides.database.TideCRUD
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TidesManagerFragment : BaseFragment(), TideCRUD {


    private var selectedGauge = Gauge.MARGATE
    private var adapter: TideManagerAdapter? = null
    private val tidesTables = HashMap<Gauge, TidesTable>(Gauge.values().size)

    override fun layoutId() = R.layout.fragment_tides_manager


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder: OnDatabaseRequestedListener = context as OnDatabaseRequestedListener
        for (gauge in Gauge.values()){
            tidesTables[gauge] = databaseHolder.onGetTableListener(gauge.id) as TidesTable
        }
    }

    override val title: String
        get() = selectedGauge.name


    override fun findViews(parent: View) {
        super.findViews(parent)
        adapter = TideManagerAdapter(this, context)

        ButterKnife.findById<ListView>(parent, R.id.list_view)
                .adapter = adapter

        ButterKnife.findById<FloatingActionButton>(parent, R.id.update_button)
                .setOnClickListener { updateTidesDatabase() }
    }


    fun updateTidesDatabase(){

    }

    override fun insert(element: TideItem) = tidesTables[selectedGauge]!!.insert(element)

    override fun insertAll(tides: Collection<TideItem>) = tidesTables[selectedGauge]!!.insertAll(tides)

    override fun read(id: DateTime) = tidesTables[selectedGauge]!!.read(id)

    override fun readAll() = tidesTables[selectedGauge]!!.readAll()

    override fun update(element: TideItem)  = tidesTables[selectedGauge]!!.update(element)

    override fun delete(element: TideItem) = tidesTables[selectedGauge]!!.delete(element)
}