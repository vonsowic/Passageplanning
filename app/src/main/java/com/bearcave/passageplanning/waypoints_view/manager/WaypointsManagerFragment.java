package com.bearcave.passageplanning.waypoints_view.manager;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.main_activity.CRUD;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointListAdapter;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointsFragment;
import com.bearcave.passageplanning.waypoints_view.manager.editor.EditorActivity;

import butterknife.OnClick;


public class WaypointsManagerFragment extends BaseWaypointsFragment implements WaypointsManagerAdapter.WaypointsListener {

    private CRUD<Waypoint> database;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        database = (CRUD) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        registerForContextMenu(getListView());
        return view;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_waypoints_manager;
    }

    @Override
    protected int listViewPlaceholderId() {
        return R.id.list_view;
    }

    @Override
    protected BaseWaypointListAdapter createAdapter() {
        return new WaypointsManagerAdapter(getContext());
    }

    @OnClick(R.id.add_new_waypoint)
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
                Waypoint result = (Waypoint) data.getSerializableExtra(EditorActivity.WAYPOINT_RESULT);
                getAdapter().addWaypoint(database.read(database.create(result)));
            }
        }
    }

    public WaypointsManagerFragment() {}

    @Override
    public void onUpdateListener(Waypoint waypoint) {
        openWaypointEditor(waypoint);
    }

    @Override
    public void delete(Waypoint waypoint) {
        database.delete(waypoint);
    }
}
