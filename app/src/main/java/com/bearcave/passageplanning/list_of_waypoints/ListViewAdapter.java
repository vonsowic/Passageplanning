package com.bearcave.passageplanning.list_of_waypoints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bearcave.passageplanning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Wąsowicz
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    //private LayoutInflater inflater;
    private WaypointsList waypoints;

    public ListViewAdapter(Context context) {
        this.context = context;
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
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //View itemView = inflater.inflate(R.layout.listview_item, parent, false);

        //TextView title = (TextView) itemView.findViewById(R.id.textTitle);
        //title.setText(articleTitles.get(position));


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        return convertView;
    }
}
