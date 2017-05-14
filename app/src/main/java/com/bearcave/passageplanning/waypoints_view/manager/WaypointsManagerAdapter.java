package com.bearcave.passageplanning.waypoints_view.manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointListAdapter;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointsFragment;

import butterknife.ButterKnife;

/**
 * Created by miwas on 13.05.17.
 */

public class WaypointsManagerAdapter extends BaseWaypointListAdapter {


    public WaypointsManagerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = getInflater().inflate(R.layout.manager_group_item, parent, false);

        TextView title = ButterKnife.findById(view, R.id.waypoint_name);
        title.setText(getWaypoint(groupPosition).getName());

        ImageView option = ButterKnife.findById(view, R.id.options_button);
        option.setOnClickListener(v -> {
            Toast.makeText(getInflater().getContext(), "HELLO", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = getInflater().inflate(R.layout.manager_item_details_item, parent, false);
        Waypoint waypoint = getWaypoint(groupPosition);

        TextView characteristic = ButterKnife.findById(view, R.id.characteristic);
        characteristic.append(waypoint.getCharacteristic());

        TextView ukc = ButterKnife.findById(view, R.id.ukc);
        ukc.append(String.valueOf(waypoint.getUkc()));

        TextView position = ButterKnife.findById(view, R.id.position);
        position.setText(String.valueOf(waypoint.getLongitude()) + "\n" + String.valueOf(waypoint.getLatitude()));

        return view;
    }


}
