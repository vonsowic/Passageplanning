package com.bearcave.passageplanning.waypoints_view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bearcave.passageplanning.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseWaypointsFragment extends Fragment implements WaypointsListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(layoutId(), container, false);

        ListView listView = ButterKnife.findById(view, listViewPlaceholderId());
        listView.setAdapter(adapter());

        return view;
    }

    protected abstract int layoutId();

    protected abstract int listViewPlaceholderId();

    protected abstract BaseWaypointListAdapter adapter();

    public BaseWaypointsFragment() { }
}
