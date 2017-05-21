package com.bearcave.passageplanning.passages;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.tables.passage.PassageDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.ReadWaypoints;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class PassageManagerAdapter extends BaseManagerAdapter {


    private ReadWaypoints waypointsDatabase;
    private SparseArray<WaypointDAO> waypoints;

    public PassageManagerAdapter(BaseManagerFragment parent, Context context) {
        super(parent, context);
        this.waypoints = new SparseArray<>();
    }


    private WaypointDAO getWaypointById(Integer id, int group){
        WaypointDAO requestedWaypoint = waypoints.get(id);

        if (requestedWaypoint == null){
            ArrayList<WaypointDAO> loaded = (ArrayList<WaypointDAO>) waypointsDatabase.read(getPassages().get(group).getWaypointsIds());
            for (WaypointDAO waypoint: loaded) {
                waypoints.put(id, waypoint);
            }
            return waypoints.get(id);
        }

        return requestedWaypoint;
    }

    private WaypointDAO getWaypointFromList(int group, int child){
        return getWaypointById(getPassages().get(group).getWaypointsIds().get(child), group);
    }

    private ArrayList<PassageDAO> getPassages(){
        return getContainer();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return getPassages().get(groupPosition).getWaypointsIds().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getWaypointFromList(groupPosition, childPosition);
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = getInflater().inflate(R.layout.passage_manager_child_item, parent, false);
        WaypointDAO waypoint = getWaypointFromList(groupPosition, childPosition);

        TextView title = ButterKnife.findById(view, R.id.name);
        title.setText(getPassages().get(groupPosition).getName());

        ImageView up = ButterKnife.findById(view, R.id.up);
        ImageView down = ButterKnife.findById(view, R.id.down);

        return view;
    }



}
