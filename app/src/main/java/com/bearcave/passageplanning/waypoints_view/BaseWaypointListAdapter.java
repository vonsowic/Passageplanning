package com.bearcave.passageplanning.waypoints_view;

import android.content.Context;
import android.widget.BaseAdapter;

import com.bearcave.passageplanning.waypoints.WaypointsList;

/**
 * Created by miwas on 12.05.17.
 */

public abstract class BaseWaypointListAdapter extends BaseAdapter {

    private Context context;
    private WaypointsListener listener;
    private WaypointsList list;


    public BaseWaypointListAdapter(Context context) {
        super();
        this.context = context;
        this.listener = (WaypointsListener) context;
        this.list = listener.onLoadListListener();
    }

    public BaseWaypointListAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
