package com.bearcave.passageplanning.waypoints_manager;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.data.database.tables.waypoints.ReadWaypoints;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointCRUD;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointsTable;
import com.bearcave.passageplanning.utils.Waypoint;

import java.util.List;

import butterknife.OnClick;


public class WaypointsManagerFragment extends BaseManagerFragment<WaypointDAO> implements WaypointCRUD{

    private WaypointsTable database;
    private WaypointsManagerAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        database = (WaypointsTable) databaseHolder.onGetTableListener(WaypointCRUD.ID);
    }

    @Override
    protected Class<?> getEditorClass() {
        return WaypointEditorActivity.class;
    }


    @Override
    protected BaseManagerAdapter createAdapter() {
        return new WaypointsManagerAdapter(this, getContext());
    }

    @Override
    public Integer insert(WaypointDAO waypointDAO) {
        return database.insert(waypointDAO);
    }

    @Override
    public WaypointDAO read(Integer id) {
        return database.read(id);
    }

    @Override
    public List<WaypointDAO> readAll() {
        return database.readAll();
    }

    @Override
    public int update(WaypointDAO waypointDAO) {
        openEditor(waypointDAO);
        return 1;
    }

    @Override
    public int delete(WaypointDAO waypointDAO) {
        return database.delete(waypointDAO);
    }

    @Override
    protected int getTitle() {
        return R.string.waypoints_menu;
    }

    public WaypointsManagerFragment() {}
}
