package com.bearcave.passageplanning.waypoints_view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints.WaypointsList;


public abstract class BaseWaypointListAdapter extends BaseExpandableListAdapter {

    private WaypointsListener listener;
    private WaypointsList list;
    private LayoutInflater inflater;


    public BaseWaypointListAdapter(Context context) {
        super();
        this.listener = (WaypointsListener) context;
        this.list = listener.onLoadListListener();
        this.inflater = LayoutInflater.from(context);
    }

    protected LayoutInflater getInflater(){
        return inflater;
    }

    protected Waypoint getWaypoint(int position){
        return list.get(position);
    }

    @Override
    public int getGroupCount() {
        return list.getSize();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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

    public void addWaypoint(Waypoint waypoint){
        list.add(waypoint);
        notifyDataSetChanged();
    }
}
