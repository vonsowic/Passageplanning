package com.bearcave.passageplanning.passage_monitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import com.bearcave.passageplanning.MainActivity

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable

class PassageActivity : AppCompatActivity(),
        ReadWaypoints {


    val waypointsTable: WaypointsTable
    val tideTables = SparseArray<TidesTable>(Gauge.values().size)
    var passage: Passage? = null
        private set

    init {
        // FIXME: maybe it could be better
        val database = MainActivity.context?.database

        waypointsTable = database?.getTable(WaypointCRUD.ID) as WaypointsTable
        for (gauge in Gauge.values()) tideTables.put(gauge.id, database.getTable(gauge.id) as TidesTable)
    }

    override fun readAllWaypoints() = waypointsTable.readAll()
    override fun readWith(ids: List<Int>): List<Waypoint> =  waypointsTable.readWith(ids)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passage)

        // receive passage and change title
        passage = intent.getParcelableExtra<Passage>(PASSAGE_KEY)
        title = passage!!.route.name

        // add fragment with content to view
        val ftransaction = supportFragmentManager.beginTransaction()
        val fragment = PassageMonitorFragment()
        val bundle = Bundle()
        bundle.putParcelable(PassageMonitorFragment.PASSAGE_KEY, passage)
        fragment.arguments = bundle
        ftransaction.replace(R.id.container, fragment)
        ftransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.passage_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val PASSAGE_KEY = "passage key"
    }
}
