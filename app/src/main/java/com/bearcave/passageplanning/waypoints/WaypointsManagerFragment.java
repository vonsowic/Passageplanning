package com.bearcave.passageplanning.waypoints;


import android.content.Context;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.waypoints.database.Waypoint;
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD;
import com.bearcave.passageplanning.waypoints.database.WaypointsTable;

import java.util.List;


public class WaypointsManagerFragment extends BaseManagerFragment<Waypoint, Integer> implements WaypointCRUD{

    private WaypointsTable database;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        database = (WaypointsTable) databaseHolder.onGetTableListener(WaypointCRUD.ID);
    }

    @Override
    protected Class<? extends WaypointEditorActivity> getEditorClass() {
        return WaypointEditorActivity.class;
    }

    @Override
    protected void onDataCreated(Waypoint result) {
        insert(result);
        getAdapter().add(result);
    }

    @Override
    protected void onDataUpdated(Waypoint result) {
        database.update(result);
        getAdapter().update(result);
    }

    @Override
    protected BaseManagerAdapter createAdapter() {
        return new WaypointsManagerAdapter(this, getContext());
    }

    @Override
    public long insert(Waypoint waypoint) {
        return database.insert(waypoint);
    }

    @Override
    public Waypoint read(Integer id) {
        return database.read(id);
    }

    @Override
    public List<Waypoint> readAll() {
        return database.readAll();
    }

    @Override
    public int update(Waypoint waypoint) {
        return database.update(waypoint);
    }

    @Override
    public int delete(Waypoint waypoint) {
        return database.delete(waypoint);
    }

    @Override
    protected int getTitle() {
        return R.string.waypoints_menu;
    }

    public WaypointsManagerFragment() {}
}
