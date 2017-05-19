package com.bearcave.passageplanning.waypoints_view.manager.editor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;


public class GaugeListAdapter extends BaseExpandableListAdapter {

    private GaugeAdapterListener listener;
    private ArrayList<Gauge> gauges;
    private LayoutInflater inflater;
    private Gauge selected ;

    public GaugeListAdapter(Context context, Gauge selected) {
        this.listener = (GaugeAdapterListener) context;
        this.inflater = LayoutInflater.from(context);
        this.gauges = new ArrayList<>(Arrays.asList(Gauge.values()));
        this.selected = selected;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return gauges.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gauges;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return gauges.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return gauges.get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view  = inflater.inflate(R.layout.editor_list_view_group, null);

        TextView text = ButterKnife.findById(view, R.id.gauge_name);
        text.setText(selected.getName());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view  = inflater.inflate(R.layout.editor_list_view_child, null);

        TextView text = ButterKnife.findById(view, R.id.gauge_name);
        text.setText(gauges.get(childPosition).getName());

        view.setOnClickListener(v -> {
            view.setSelected(true);
            selected = gauges.get(childPosition);
            listener.onItemSelectedListener(selected);
            notifyDataSetChanged();
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    interface GaugeAdapterListener{
        void onItemSelectedListener(Gauge gauge);
    }
}
