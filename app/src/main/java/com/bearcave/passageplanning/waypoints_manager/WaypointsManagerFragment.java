package com.bearcave.passageplanning.waypoints_manager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointCRUD;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointsTable;
import com.bearcave.passageplanning.utils.Waypoint;
import com.bearcave.passageplanning.waypoints_manager.editor.EditorActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class WaypointsManagerFragment extends Fragment implements WaypointCRUD{

    private WaypointsTable database;
    private WaypointsManagerAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        database = (WaypointsTable) databaseHolder.onGetTableListener(WaypointCRUD.ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waypoints_manager, container, false);

        ExpandableListView listView = ButterKnife.findById(view, R.id.list_view);
        adapter = new WaypointsManagerAdapter(this, getContext());
        listView.setAdapter(adapter);

        view.findViewById(R.id.add_new_waypoint).setOnClickListener( v-> openWaypointEditor(null));

        return view;
    }



    public void openWaypointEditor() {
        openWaypointEditor(null);
    }


    public void openWaypointEditor(Waypoint waypoint){
        Intent intent = new Intent(getContext(), EditorActivity.class);

        if(waypoint != null){
            intent.putExtra(EditorActivity.WAYPOINT_RESULT, waypoint);
        }

        startActivityForResult(intent, EditorActivity.WAYPOINT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditorActivity.WAYPOINT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                WaypointDAO result = (WaypointDAO) data.getSerializableExtra(EditorActivity.WAYPOINT_RESULT);
                adapter.addWaypoint(database.read(database.insert(result)));
            }
        }
    }

    public WaypointsManagerFragment() {}


    @Override
    public Integer insert(WaypointDAO waypointDAO) {
        return database.insert(waypointDAO);
    }

    @Override
    public WaypointDAO read(Integer id) {
        return database.read(id);
    }

    @Override
    public List<WaypointDAO> readAll() {
        return database.readAll();
    }

    @Override
    public int update(WaypointDAO waypointDAO) {
        return database.update(waypointDAO);
    }

    @Override
    public int delete(WaypointDAO waypointDAO) {
        return database.delete(waypointDAO);
    }
}
