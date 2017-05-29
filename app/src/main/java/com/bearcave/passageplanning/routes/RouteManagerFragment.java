package com.bearcave.passageplanning.routes;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.routes.database.RouteCRUD;
import com.bearcave.passageplanning.routes.database.Route;
import com.bearcave.passageplanning.routes.database.RouteTable;
import com.bearcave.passageplanning.routes.editor.RouteEditorActivity;
import com.bearcave.passageplanning.waypoints.database.Waypoint;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteManagerFragment extends BaseManagerFragment<Route, Integer>
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
    protected Class<? extends BaseEditorActivity<Route>> getEditorClass() {
        return RouteEditorActivity.class;
    }

    @Override
    public long insert(Route route) {
        return database.insert(route);
    }

    @Override
    public Route read(Integer id) {
        return database.read(id);
    }

    @Override
    public List<Route> readAll() {
        return database.readAll();
    }

    // TODO: implement
    @Override
    public int update(Route element) {
        return 0;
    }

    @Override
    public int delete(Route element) {
        return database.delete(element);
    }

    @Override
    protected void putExtra(Intent mail) {
        ArrayList<Waypoint> waypoints = (ArrayList<Waypoint>) readAllWaypoints();

        mail.putParcelableArrayListExtra(
                RouteEditorActivity.WAYPOINTS_KEY,
                waypoints
        );
    }

    @Override
    protected void onDataCreated(Route result) {
        insert(result);
        getAdapter().add(result);
    }

    @Override
    protected void onDataUpdated(Route result) {
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
    public List<Waypoint> readAllWaypoints() {
        return waypointsHolder.readAllWaypoints();
    }
}
