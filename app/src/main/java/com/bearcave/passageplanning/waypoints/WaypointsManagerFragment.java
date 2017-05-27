package com.bearcave.passageplanning.waypoints;


import android.content.Context;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD;
import com.bearcave.passageplanning.waypoints.database.WaypointDAO;
import com.bearcave.passageplanning.waypoints.database.WaypointsTable;

import java.util.List;


public class WaypointsManagerFragment extends BaseManagerFragment<WaypointDAO, Integer> implements WaypointCRUD{

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
    protected void onDataCreated(WaypointDAO result) {
        insert(result);
        getAdapter().add(result);
    }

    @Override
    protected void onDataUpdated(WaypointDAO result) {
        database.update(result);
        getAdapter().update(result);
    }


    @Override
    protected BaseManagerAdapter createAdapter() {
        return new WaypointsManagerAdapter(this, getContext());
    }

    @Override
    public long insert(WaypointDAO waypointDAO) {
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