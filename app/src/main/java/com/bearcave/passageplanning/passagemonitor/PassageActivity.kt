package com.bearcave.passageplanning.passagemonitor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.passagemonitor.passage_list_adapter.PassageMonitorAdapter
import com.bearcave.passageplanning.passagemonitor.pdfviewer.PassagePlanViewerActivity
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.passages.planner.PassagePlan
import com.bearcave.passageplanning.passages.planner.PlanGetter
import com.bearcave.passageplanning.tides.database.TidesTable
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable
import org.joda.time.DateTime

class PassageActivity : AppCompatActivity(),
        ReadWaypoints,
        PlanGetter,
        PassageMonitorAdapter.PassageMonitor,
        FootFragment.FootListener{

    var passage: Passage? = null
        private set

    var passagePlan: PassagePlan? = null

    override fun getPlan() = passagePlan!!


    private val passageFragment: PassageMonitorFragment = PassageMonitorFragment()
    private val waypointsTable: WaypointsTable
    private val tideTables = SparseArray<TidesTable>(Gauge.values().size)

    private val foot = FootFragment()


    init {
        // FIXME: maybe it could be better
        val database = DatabaseManager.DATABASE_MANAGER

        waypointsTable = database.getTable(WaypointCRUD.ID) as WaypointsTable
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

        passagePlan = PassagePlan(
                passage!!,
                readWith(passage!!.route.waypointsIds) as ArrayList<Waypoint>
        )

        val manager = supportFragmentManager
        // add fragment with content to view
        var ftransaction = manager.beginTransaction()
        ftransaction.replace(R.id.container, passageFragment)
        ftransaction.commit()

        ftransaction = manager.beginTransaction()
        val footBundle = Bundle()
        footBundle.putString(FootFragment.WAYPOINT_KEY, passagePlan?.lastWaypoint?.name ?: "")
        foot.arguments = footBundle
        ftransaction.replace(R.id.last_waypoint, foot)
        ftransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.passage_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_generate_doc) {
            val viewer = Intent(this, PassagePlanViewerActivity::class.java)
            viewer.putExtra(PassagePlanViewerActivity.PASSAGE_PLAN_KEY, passageFragment.passagePlan)
            startActivity(viewer)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val PASSAGE_KEY = "passage key"
    }

    override fun setToGo(toGo: Float) {
        foot.setToGo(toGo)
    }

    override fun setCourse(course: Float) {
        foot.setCourse(course)
    }

    override fun setEta(eta: DateTime) {
        foot.setEta(eta)
    }

    override fun onSpeedChangedLister(speed: Float) {
        passageFragment.onSpeedChangedLister(speed)
    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Are you sure you want to leave?")
        alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                "Yes",
                { _, _ -> run {
                    super.onBackPressed()
                }}
        )

        alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                "No",
                { _, _ -> run{}}
        )

        alertDialog.show()
    }
}
