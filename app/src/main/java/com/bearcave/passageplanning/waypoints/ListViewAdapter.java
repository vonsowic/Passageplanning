package com.bearcave.passageplanning.waypoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bearcave.passageplanning.R;

/**
 * @author Michał Wąsowicz
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    //private LayoutInflater inflater;
    private WaypointsList waypoints;

    public ListViewAdapter(Context context) {
        this.context = context;

        // TODO: remove when necessary
        // TEST

        waypoints = new WaypointsList();
        waypoints.add(
                new Waypoint(
                        1,
                        "PIerwszy",
                        "opis",
                        0,
                        0,
                        0
                )
        );
        waypoints.add(
                new Waypoint(
                        2,
                        "Drugi",
                        "opis",
                        0,
                        0,
                        0
                )
        );
    }

    @Override
    public int getCount() {
        return waypoints.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);

        Waypoint waypoint = waypoints.get(position);

        TextView name = (TextView) itemView.findViewById(R.id.waypoint);
        name.setText(waypoint.getName());

        TextView ukc = (TextView) itemView.findViewById(R.id.ukc);
        ukc.setText( String.valueOf(waypoint.getUkc()));

        TextView bearing  = (TextView) itemView.findViewById(R.id.bearing);
        //bearing.setText(waypoint.getName());

        TextView distance = (TextView) itemView.findViewById(R.id.dist);
        //distance.setText(waypoint.getName());


        return itemView;
    }
}
