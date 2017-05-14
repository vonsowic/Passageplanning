package com.bearcave.passageplanning.waypoints_view.manager;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointListAdapter;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointsFragment;

import butterknife.OnClick;


public class WaypointsManagerFragment extends BaseWaypointsFragment {


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
    public void addNewWaypoint(){
        Intent intent = new Intent(getContext(), EditorActivity.class);
        startActivityForResult(intent, EditorActivity.WAYPOINT_REQUEST);
    }

    public WaypointsManagerFragment() {}
}
