package com.bearcave.passageplanning.main_activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.main_activity.data.DatabaseManager;
import com.bearcave.passageplanning.main_activity.data.FilesManager;
import com.bearcave.passageplanning.passages.PassagesManagerFragment;
import com.bearcave.passageplanning.reports.ReportsManagerFragment;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints_view.manager.WaypointsManagerFragment;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CRUD<Waypoint> {

    private DatabaseManager database;
    private FilesManager files;
    private static final int MY_PERMISSIONS_REQUEST_FILE = 54;

    private final HashMap<Integer, Fragment> fragmentHolder = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentHolder.put(R.id.nav_passages_menu, new PassagesManagerFragment());
        fragmentHolder.put(R.id.nav_waypoints_menu, new WaypointsManagerFragment());
        fragmentHolder.put(R.id.nav_reports_menu_menu, new ReportsManagerFragment());

        askForPermission();
    }

    private void afterPermissionIsChecked(){
        files = new FilesManager(this);
        database = files.createDatabase();
        showFragment(R.id.nav_waypoints_menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showFragment(int id){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, fragmentHolder.get(id));
        ft.commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        showFragment(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public long create(Waypoint waypoint) {
        long id = database.addWaypoint(waypoint);
        Toast.makeText(this, "New wayponit added " + id, Toast.LENGTH_LONG).show();
        return id;
    }

    @Override
    public Waypoint read(long id) {
        return database.getWaypoint(id);
    }

    @Override
    public List<Waypoint> readAll() {
        return database.getAllWaypoints();
    }

    @Override
    public int update(Waypoint waypoint) {
        return database.updateWaypoint(waypoint);
    }

    @Override
    public int delete(Waypoint waypoint) {
        return database.deleteWaypoint(waypoint);
    }

    private void askForPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    MY_PERMISSIONS_REQUEST_FILE
            );
        } else {
            afterPermissionIsChecked();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FILE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    afterPermissionIsChecked();
                } else {
                    Toast.makeText(this, "Application cannot work properly without this permission", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }
}
