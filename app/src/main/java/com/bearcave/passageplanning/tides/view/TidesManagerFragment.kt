package com.bearcave.passageplanning.tides.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseFragment
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.tasks.TaskUpdaterListener
import com.bearcave.passageplanning.tasks.UpdateTideTablesTask
import com.bearcave.passageplanning.tides.database.TideCRUD
import com.bearcave.passageplanning.tides.database.TideItem
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.tides.web.configurationitems.MinuteStep
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TidesManagerFragment : BaseFragment(), TideCRUD, TaskUpdaterListener {

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
        get() = selectedGauge.humanCode


    override fun findViews(parent: View) {
        super.findViews(parent)
        setHasOptionsMenu(true)

        adapter = TideManagerAdapter(this)
        ButterKnife.findById<ListView>(parent, R.id.list_view)
                .adapter = adapter

        ButterKnife.findById<FloatingActionButton>(parent, R.id.reload)
                .setOnClickListener { adapter!!.reload() }

        ButterKnife.findById<FloatingActionButton>(parent, R.id.update_button)
                .setOnClickListener { updateTidesDatabase() }
    }


    override fun onCreateOptionsMenu(optionsMenu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(optionsMenu, inflater)
        inflater?.inflate(R.menu.tide_manager_menu, optionsMenu)

        // add all gauges to menu
        for (gauge in tidesTables.keys){
            optionsMenu?.add(Menu.NONE, gauge.id, Menu.NONE, gauge.humanCode)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ) {
            R.id.filter_date -> showFilterDateMenu(ButterKnife.findById(activity, R.id.filter_date))
            R.id.filter_step -> showFilterStepMenu(ButterKnife.findById(activity, R.id.filter_step))
            R.id.filter_tides-> adapter!!.filterOnlyTides()
            else -> {
               selectedGauge = Gauge.getById(item?.itemId!!)
               adapter!!.reload()
               activity.title = title
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showFilterDateMenu(anchor: View){
        val menu = PopupMenu(context, anchor)
        menu.inflate(R.menu.tide_filter_date_menu)

        menu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.today ->       adapter!!.dateFilter = TideManagerAdapter.Companion::TODAY
                R.id.tomorrow ->    adapter!!.dateFilter = TideManagerAdapter.Companion::TOMORROW
                R.id.week ->        adapter!!.dateFilter = TideManagerAdapter.Companion::WEEK
                R.id.all ->         adapter!!.dateFilter = TideManagerAdapter.Companion::ALL
            }
            true
        }

        menu.show()
    }


    private fun showFilterStepMenu(anchor: View){
        val menu = PopupMenu(context, anchor)
        menu.inflate(R.menu.tide_filter_step_menu)

        for (step in MinuteStep.values()){
            menu.menu.add(Menu.NONE, step.value, Menu.NONE, "${step.value} minutes")
        }

        menu.setOnMenuItemClickListener { item ->
            adapter!!.stepFilter = MinuteStep.getByValue(item.itemId)
            true
        }

        menu.show()
    }


    fun updateTidesDatabase(){
        UpdateTideTablesTask(this).execute(*Gauge.values())
    }

    override fun insert(element: TideItem) = tidesTables[selectedGauge]!!.insert(element)

    override fun insertAll(tides: Collection<TideItem>) = tidesTables[selectedGauge]!!.insertAll(tides)

    override fun read(id: DateTime) = tidesTables[selectedGauge]!!.read(id)

    override fun readAll() = tidesTables[selectedGauge]!!.readAll()

    override fun update(element: TideItem)  = tidesTables[selectedGauge]!!.update(element)

    override fun delete(element: TideItem) = tidesTables[selectedGauge]!!.delete(element)

    override fun onNoInternetConnection() {
        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
    }

    override fun onTaskFinished() {
        adapter!!.reload()
    }
}