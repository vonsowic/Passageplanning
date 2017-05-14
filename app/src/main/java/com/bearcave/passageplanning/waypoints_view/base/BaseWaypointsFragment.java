package com.bearcave.passageplanning.waypoints_view.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseWaypointsFragment extends Fragment {

    protected BaseWaypointListAdapter adapter;
    private Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(layoutId(), container, false);

        ExpandableListView listView = ButterKnife.findById(view, listViewPlaceholderId());

        adapter = createAdapter();
        listView.setAdapter(adapter);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    protected abstract int layoutId();

    protected abstract int listViewPlaceholderId();

    protected abstract BaseWaypointListAdapter createAdapter();

    protected BaseWaypointListAdapter getAdapter(){
        return adapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public BaseWaypointsFragment() { }
}
