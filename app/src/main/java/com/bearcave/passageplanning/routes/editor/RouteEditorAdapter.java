package com.bearcave.passageplanning.routes.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.database.WaypointDAO;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by miwas on 23.05.17, because ContextMenu hides after each click, OptionsMenu doesn't look good and list with CheckedTextView sucks.
 */

public class RouteEditorAdapter extends BaseAdapter {

    private ArrayList<WaypointDAO> waypoints;
    private Context context;
    private OnItemClickedListener listener;
    private LayoutInflater inflater;

    public RouteEditorAdapter( Context context, ArrayList<WaypointDAO> waypoints) {
        this.context = context;
        this.listener = (OnItemClickedListener) context;
        this.waypoints = waypoints;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return waypoints.size();
    }

    @Override
    public Object getItem(int position) {
        return waypoints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return waypoints.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.route_editor_list_item, parent, false);

        WaypointDAO wpt = waypoints.get(position);

        TextView view = ButterKnife.findById(convertView, R.id.waypoint_name);
        view.setText(wpt.getName());

        ImageView checkbox = ButterKnife.findById(convertView, R.id.checkbox);
        setChecked(checkbox, listener.isItemCheckedListener(wpt.getId()));

        convertView.setOnClickListener( v-> {
            listener.onItemCheckListener(wpt.getId());
            setChecked(checkbox, listener.isItemCheckedListener(wpt.getId()));
        });

        return convertView;
    }

    private void setChecked(ImageView view, boolean checked){
        if(checked){
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    interface OnItemClickedListener{
        boolean isItemCheckedListener(int id);
        void onItemCheckListener(int id);
    }
}
