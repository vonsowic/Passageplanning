package com.bearcave.passageplanning;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bearcave.passageplanning.data.FilesManager;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints.WaypointsList;
import com.bearcave.passageplanning.waypoints_view.base.WaypointsListener;
import com.bearcave.passageplanning.waypoints_view.manager.WaypointsManagerFragment;

public class TestActivity extends AppCompatActivity implements WaypointsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new WaypointsManagerFragment());
        ft.commit();

        FilesManager manager = new FilesManager(this);
    }

    @Override
    public WaypointsList onLoadListListener() {
        WaypointsList list = new WaypointsList();
        list.add(new Waypoint(
                1,
                "Pierwszy",
                "opis 1",
                0,
                0,
                0
        ));

        list.add(new Waypoint(
                2,
                "Drugi",
                "opis 2",
                0,
                0,
                0
        ));

        list.add(new Waypoint(
                3,
                "Trzeci",
                "opis 3",
                0,
                0,
                0
        ));
        return list;
    }

}
