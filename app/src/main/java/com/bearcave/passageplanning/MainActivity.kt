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
import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.data.FilesManager
import com.bearcave.passageplanning.data.database.DatabaseManager
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.passage.PassageManagerFragment
import com.bearcave.passageplanning.passage.database.ReadRoutes
import com.bearcave.passageplanning.routes.ReadWaypoints
import com.bearcave.passageplanning.routes.RouteManagerFragment
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.routes.database.RouteCRUD
import com.bearcave.passageplanning.routes.database.RouteTable
import com.bearcave.passageplanning.settings.SettingsFragment
import com.bearcave.passageplanning.waypoints.WaypointsManagerFragment
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD
import com.bearcave.passageplanning.waypoints.database.WaypointsTable

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnDatabaseRequestedListener, ReadWaypoints, ReadRoutes {

    private var database: DatabaseManager? = null
    private var files: FilesManager? = null

    private val fragmentHolder = SparseArray<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        fragmentHolder.put(R.id.nav_routes_menu, RouteManagerFragment())
        fragmentHolder.put(R.id.nav_waypoints_menu, WaypointsManagerFragment())
        fragmentHolder.put(R.id.nav_passages_menu, PassageManagerFragment())
        fragmentHolder.put(R.id.nav_settings, SettingsFragment())

        askForPermission()
    }

    private fun afterPermissionIsChecked() {
        files = FilesManager(this)
        database = files!!.createDatabase()
        showFragment(R.id.nav_waypoints_menu)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun showFragment(id: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, fragmentHolder.get(id))
        ft.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        showFragment(item.itemId)
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_FILE
            )
        } else {
            afterPermissionIsChecked()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FILE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    afterPermissionIsChecked()
                } else {
                    Toast.makeText(this, "Application cannot work properly without this permission", Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
        }
    }

    override fun onGetTableListener(tableId: Int): BaseTableWithCustomKey<*, *> {
        return database!!.getTable(tableId)
    }

    override fun readAllWaypoints(): List<Waypoint> {
        val databaseTable = database!!.getTable(WaypointCRUD.ID) as WaypointsTable
        return databaseTable.readAll()
    }

    override fun readAllRoutes(): List<Route> {
        val databaseTable = database!!.getTable(RouteCRUD.ID) as RouteTable
        return databaseTable.readAll()
    }

    companion object {
        private val MY_PERMISSIONS_REQUEST_FILE = 54
    }
}