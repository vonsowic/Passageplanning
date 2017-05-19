package com.bearcave.passageplanning.waypoints_view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

import com.bearcave.passageplanning.main_activity.CRUD;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints.Waypoints;

import java.util.List;


public abstract class BaseWaypointListAdapter extends BaseExpandableListAdapter {

    private CRUD<Waypoint> database;
    private Waypoints waypoints;
    private Context context;


    public BaseWaypointListAdapter(Context context) {
        super();
        this.database = (CRUD) context;
        this.context = context;

        //this.waypoints = new Waypoints();
        this.waypoints = (Waypoints) database.readAll();
    }

    protected LayoutInflater getInflater(){
        return LayoutInflater.from(context);
    }

    protected Context getContext(){
        return context;
    }

    protected Waypoint getWaypoint(int position){
        return waypoints.get(position);
    }

    public void setWaypoints(List waypoints){
        this.waypoints = (Waypoints) waypoints;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return waypoints.getSize();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return waypoints.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return waypoints.get(groupPosition).getId();
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
        waypoints.add(waypoint);
        notifyDataSetChanged();
    }
}
