package com.bearcave.passageplanning.waypoints_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.bearcave.passageplanning.waypoints.WaypointsList;

/**
 * Created by miwas on 12.05.17.
 */

public abstract class BaseWaypointListAdapter extends BaseExpandableListAdapter {

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
    public int getGroupCount() {
        return list.getSize();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return list.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
