package com.bearcave.passageplanning

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.MenuItem
import android.widget.Toast
import butterknife.ButterKnife
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.data.FilesManager
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.passages.PassageManagerFragment
import com.bearcave.passageplanning.passages.database.ReadRoutes
import com.bearcave.passageplanning.routes.RouteManagerFragment
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.database.RouteTable
import com.bearcave.passageplanning.tasks.TideManagerService
import com.bearcave.passageplanning.tides.view.TidesManagerFragment
import com.bearcave.passageplanning.waypoints.WaypointsManagerFragment
import com.bearcave.passageplanning.waypoints.database.ReadWaypoints
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable
import org.joda.time.DateTime


class MainActivity
    : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        OnDatabaseRequestedListener,
        ReadWaypoints,
        ReadRoutes,
        TideManagerService.TideManagerListener
{

    override fun onNoInternetConnection() {
        Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
    }

    var database: DatabaseManager? = null
        private set

    private var files: FilesManager? = null

    private val fragmentHolder = SparseArray<Lazy<Fragment>>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = ButterKnife.findById<Toolbar>(this, R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = ButterKnife.findById<DrawerLayout>(this, R.id.drawer_layout)
        ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            .syncState()

        ButterKnife.findById<NavigationView>(this, R.id.nav_view)
            .setNavigationItemSelectedListener(this)


        fragmentHolder.put(R.id.nav_routes_menu, lazy { RouteManagerFragment() })
        fragmentHolder.put(R.id.nav_waypoints_menu, lazy { WaypointsManagerFragment() })
        fragmentHolder.put(R.id.nav_passages_menu, lazy { PassageManagerFragment() })
        fragmentHolder.put(R.id.nav_tides_menu, lazy { TidesManagerFragment() })
        //fragmentHolder.put(R.id.nav_settings, SettingsFragment())

        askForPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PASSAGE_PERMISSIONS_REQUEST_FILE,
                { afterFilePermissionIsChecked() }
        )
    }

    private fun afterFilePermissionIsChecked() {
        files = FilesManager(this)
        database = files!!.createDatabase()

        val date = DateTime.now()
        if( date.monthOfYear == 7 && date.dayOfMonth == 27){
            showFragment(1234)
        } else {
            showFragment(R.id.nav_passages_menu)
        }
    }

    override fun onBackPressed() {
        val drawer = ButterKnife.findById<DrawerLayout>(this, R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun showFragment(id: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, fragmentHolder.get(id).value)
        ft.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        showFragment(item.itemId)
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun askForPermission(permission: String, requestCode: Int, onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    requestCode
            )
        } else {
            onPermissionGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PASSAGE_PERMISSIONS_REQUEST_FILE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    afterFilePermissionIsChecked()
                } else {
                    Toast.makeText(this, "Application cannot work properly without this permission", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    override fun onGetTableListener(tableId: Int): BaseTableWithCustomKey<*, *> = database!!.getTable(tableId)

    override fun readAllWaypoints(): List<Waypoint> {
        val databaseTable = database!!.getTable(WaypointCRUD.ID) as WaypointsTable
        return databaseTable.readAll()
    }

    override fun readWith(ids: List<Int>) = (database!!.getTable(WaypointCRUD.ID) as WaypointsTable)
            .readWith(ids)


    override fun readAllRoutes(): List<Route> {
        val databaseTable = database!!.getTable(RouteCRUD.ID) as RouteTable
        return databaseTable.readAll()
    }

    companion object {
        val PASSAGE_PERMISSIONS_REQUEST_FILE = 1
    }
}