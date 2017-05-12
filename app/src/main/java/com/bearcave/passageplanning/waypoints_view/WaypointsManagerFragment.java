package com.bearcave.passageplanning.waypoints_view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.WaypointsList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaypointsManagerFragment extends BaseWaypointsFragment {


    public WaypointsManagerFragment() {
        // Required empty public constructor
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_waypoints_manager;
    }

    @Override
    protected int listViewPlaceholderId() {
        return 0;
    }

    @Override
    protected BaseWaypointListAdapter adapter() {
        return null;
    }

    @Override
    public WaypointsList onLoadListListener() {
        return null;
    }
}
