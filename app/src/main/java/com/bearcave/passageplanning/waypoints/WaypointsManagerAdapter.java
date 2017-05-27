package com.bearcave.passageplanning.waypoints;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.waypoints.database.WaypointDAO;

import butterknife.ButterKnife;



public class WaypointsManagerAdapter extends BaseManagerAdapter<WaypointDAO, Integer>{


    public WaypointsManagerAdapter(BaseManagerFragment parent, Context context) {
        super(parent, context);
        addOption("Edit", element -> getDatabase().update(element));
        addOption("Delete", element -> {
            getDatabase().delete(element);
            getContainer().remove(element);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return getContainer().get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = getInflater().inflate(R.layout.manager_item_details_item, parent, false);
        Waypoint waypoint = getContainer().get(groupPosition);

        TextView note = ButterKnife.findById(view, R.id.note);
        note.append(waypoint.getNote());

        TextView characteristic = ButterKnife.findById(view, R.id.characteristic);
        characteristic.append(waypoint.getCharacteristic());

        TextView gauge = ButterKnife.findById(view, R.id.gauge);
        gauge.append(waypoint.getGauge().getName());

        TextView ukc = ButterKnife.findById(view, R.id.ukc);
        ukc.append(String.valueOf(waypoint.getUkc()));

        TextView position = ButterKnife.findById(view, R.id.position);
        position.setText(String.valueOf(waypoint.getLongitude()) + "\n" + String.valueOf(waypoint.getLatitude()));

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}

