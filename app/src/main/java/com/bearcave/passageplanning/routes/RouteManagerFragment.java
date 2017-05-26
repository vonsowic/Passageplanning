package com.bearcave.passageplanning.routes;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.data.database.tables.route.RouteCRUD;
import com.bearcave.passageplanning.data.database.tables.route.RouteDAO;
import com.bearcave.passageplanning.data.database.tables.route.RouteTable;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteManagerFragment extends BaseManagerFragment<RouteDAO, Integer>
        implements RouteCRUD,
        ReadWaypoints{

    private RouteTable database;
    private ReadWaypoints waypointsHolder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        waypointsHolder = (ReadWaypoints) context;
        database = (RouteTable) databaseHolder.onGetTableListener(RouteCRUD.ID);
    }

    @Override
    protected Class<? extends BaseEditorActivity<RouteDAO>> getEditorClass() {
        return RouteEditorActivity.class;
    }

    @Override
    public long insert(RouteDAO route) {
        return database.insert(route);
    }

    @Override
    public RouteDAO read(Integer id) {
        return database.read(id);
    }

    @Override
    public List<RouteDAO> readAll() {
        return database.readAll();
    }

    // TODO: implement
    @Override
    public int update(RouteDAO element) {
        return 0;
    }

    @Override
    public int delete(RouteDAO element) {
        return database.delete(element);
    }

    @Override
    protected void putExtra(Intent mail) {
        ArrayList<WaypointDAO> waypoints = (ArrayList<WaypointDAO>) readAllWaypoints();

        mail.putParcelableArrayListExtra(
                RouteEditorActivity.WAYPOINTS_KEY,
                waypoints
        );
    }

    @Override
    protected void onDataCreated(RouteDAO result) {
        insert(result);
        getAdapter().add(result);
    }

    @Override
    protected void onDataUpdated(RouteDAO result) {
        database.update(result);
        getAdapter().update(result);
    }

    @Override
    protected BaseManagerAdapter createAdapter() {
        return new RouteManagerAdapter(this, getContext());
    }

    @Override
    protected int getTitle() {
        return R.string.routes_menu;
    }

    public RouteManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public List<WaypointDAO> readAllWaypoints() {
        return waypointsHolder.readAllWaypoints();
    }
}
