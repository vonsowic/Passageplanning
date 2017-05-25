package com.bearcave.passageplanning.routes;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.tables.route.RouteDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class RouteManagerAdapter extends BaseManagerAdapter {


    private ReadWaypoints waypointsDatabase;
    private SparseArray<WaypointDAO> waypoints;

    public RouteManagerAdapter(BaseManagerFragment parent, Context context) {
        super(parent, context);
        this.waypoints = new SparseArray<>();
        this.waypointsDatabase = (ReadWaypoints) context;

        for(WaypointDAO waypoint : waypointsDatabase.readAllWaypoints()){
            waypoints.put(waypoint.getId(), waypoint);
        }
    }


    private WaypointDAO getWaypointById(Integer id){
        return waypoints.get(id);
    }

    private WaypointDAO getWaypointFromList(int group, int child){
        return getWaypointById(getPassages().get(group).getWaypointsIds().get(child));
    }

    private ArrayList<RouteDAO> getPassages(){
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
        title.setText(waypoint.getName());

        ImageView up = ButterKnife.findById(view, R.id.up);
        ImageView down = ButterKnife.findById(view, R.id.down);

        return view;
    }
}
