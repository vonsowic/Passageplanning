package com.bearcave.passageplanning.waypoints_view.manager;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints_view.base.BaseWaypointListAdapter;

import butterknife.ButterKnife;

/**
 * Created by miwas on 13.05.17.
 */

public class WaypointsManagerAdapter extends BaseWaypointListAdapter implements View.OnCreateContextMenuListener{


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
            //v.registerForContextMenu(option);
            //WaypointsManagerAdapter.this.openContextMenu(option);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Toast.makeText(getContext(), "TEst", Toast.LENGTH_LONG).show();
        //Activity.getMenuInflater().inflate(R.menu.manager_menu, menu);
    }

    interface WaypointsListener{
        void onUpdateListener(Waypoint waypoint);
        void delete(Waypoint waypoint);
    }
}
