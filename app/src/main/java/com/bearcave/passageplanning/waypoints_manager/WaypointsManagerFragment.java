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
import com.bearcave.passageplanning.waypoints_manager.editor.WaypointEditorActivity;

import java.util.List;

import butterknife.OnClick;


public class WaypointsManagerFragment extends BaseManagerFragment implements WaypointCRUD, ReadWaypoints{

    private WaypointsTable database;
    private WaypointsManagerAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        database = (WaypointsTable) databaseHolder.onGetTableListener(WaypointCRUD.ID);
    }

    @Override
    protected BaseManagerAdapter getAdapter() {
        adapter = new WaypointsManagerAdapter(this, getContext());
        return adapter;
    }

    @OnClick(R.id.open_editor)
    public void openWaypointEditor() {
        openWaypointEditor(null);
    }

    public void openWaypointEditor(Waypoint waypoint){
        Intent intent = new Intent(getContext(), WaypointEditorActivity.class);

        if(waypoint != null){
            intent.putExtra(WaypointEditorActivity.EDITOR_RESULT, waypoint);
        }

        startActivityForResult(intent, WaypointEditorActivity.EDITOR_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WaypointEditorActivity.EDITOR_REQUEST) {
            if (resultCode == WaypointEditorActivity.EDITOR_CREATED) {
                WaypointDAO result = (WaypointDAO) data.getSerializableExtra(WaypointEditorActivity.EDITOR_RESULT);

                if( result == null) {
                    Toast.makeText(getContext(), "Ups! Waypoint wasn't created", Toast.LENGTH_SHORT).show();
                    return;
                }

                // send waypoint that was saved in database instead of sending waypoint from WaypointEditorActivity
                adapter.add(
                        read(insert(result))
                );

            } else if (resultCode == WaypointEditorActivity.EDITOR_UPDATED) {
                WaypointDAO result = (WaypointDAO) data.getSerializableExtra(WaypointEditorActivity.EDITOR_RESULT);

                if( result == null) {
                    Toast.makeText(getContext(), "Ups! Waypoint wasn't updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.update(result);
                adapter.add(result);
            }
        }
    }

    @Override
    public Integer insert(WaypointDAO waypointDAO) {
        return database.insert(waypointDAO);
    }

    @Override
    public List<WaypointDAO> read(List<Integer> ids) {
        return database.read(ids);
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
        openWaypointEditor(waypointDAO);
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
