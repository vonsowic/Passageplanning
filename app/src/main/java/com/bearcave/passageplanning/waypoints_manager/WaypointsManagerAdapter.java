package com.bearcave.passageplanning.waypoints_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointCRUD;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.utils.Waypoint;

import java.util.ArrayList;

import butterknife.ButterKnife;



public class WaypointsManagerAdapter extends BaseExpandableListAdapter{

    private final WaypointCRUD listener;
    private ArrayList<WaypointDAO> waypoints;
    private LayoutInflater inflater;
    private Context context;

    public WaypointsManagerAdapter(WaypointCRUD fragment, Context context) {
        this.listener = fragment;
        inflater = LayoutInflater.from(context);
        waypoints = (ArrayList<WaypointDAO>) listener.readAll();
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return waypoints.size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.manager_group_item, parent, false);

        TextView title = ButterKnife.findById(view, R.id.waypoint_name);
        title.setText(waypoints.get(groupPosition).getName());

        ImageView option = ButterKnife.findById(view, R.id.options_button);
        option.setOnClickListener(v -> showPopupMenu(v, waypoints.get(groupPosition)));

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.manager_item_details_item, parent, false);
        Waypoint waypoint = waypoints.get(groupPosition);

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

    private void showPopupMenu(View anchor, WaypointDAO waypoint){
        PopupMenu menu = new PopupMenu(context, anchor);
        menu.getMenu().add(Menu.NONE, EDIT_CODE, Menu.NONE, context.getString(R.string.action_edit));
        menu.getMenu().add(Menu.NONE, DELETE_CODE, Menu.NONE, context.getString(R.string.action_delete));

        menu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case EDIT_CODE:
                    listener.update(waypoint);
                    break;
                case DELETE_CODE:
                    listener.delete(waypoint);
                    break;
                default:
                    return false;
            }

            return true;
        });

        menu.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void addWaypoint(WaypointDAO waypoint){
        waypoints.add(waypoint);
        notifyDataSetChanged();
    }

    private static final int EDIT_CODE = 1;
    private static final int DELETE_CODE = 2;

}

