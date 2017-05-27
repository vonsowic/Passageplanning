package com.bearcave.passageplanning.routes.editor;

import android.content.Intent;
import android.widget.ListView;
import android.widget.EditText;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.routes.database.route.RouteDAO;
import com.bearcave.passageplanning.waypoints.database.WaypointDAO;

import java.util.ArrayList;
import java.util.HashSet;


public class RouteEditorActivity extends BaseEditorActivity<RouteDAO> implements RouteEditorAdapter.OnItemClickedListener {

    private EditText name;
    private ListView waypointChooser;

    private HashSet<Integer> chosenWaypoints = new HashSet<>();
    private ArrayList<WaypointDAO> waypoints = new ArrayList<>();

    private Integer routeId = -2;

    @Override
    protected void getParcelableExtra(Intent intent) {
        ArrayList tmpList = intent.getParcelableArrayListExtra(WAYPOINTS_KEY);
        waypoints = tmpList != null ? tmpList : waypoints;
    }

    @Override
    protected void findViews() {
        super.findViews();
        name = (EditText) findViewById(R.id.name_text);
        waypointChooser = (ListView) findViewById(R.id.waypoints_list);
        waypointChooser.setAdapter(
                new RouteEditorAdapter(this, waypoints)
        );
    }

    @Override
    protected void setViewsContent(RouteDAO passage) {
        routeId = passage.getId();
        name.setText(passage.getName());
        chosenWaypoints = new HashSet<>(passage.getWaypointsIds());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.content_route_editor;
    }

    @Override
    public boolean isAllFilled() {
        return name.getText().length() != 0 && chosenWaypoints.size() != 0;
    }

    @Override
    protected RouteDAO getFilledDAO() {
        return new RouteDAO(
                routeId,
                name.getText().toString(),
                new ArrayList<>(chosenWaypoints)
        );
    }


    public static final String WAYPOINTS_KEY = "waypoints_from_database";

    @Override
    public boolean isItemCheckedListener(int id) {
        return chosenWaypoints.contains(id);
    }

    @Override
    public void onItemCheckListener(int id) {
        if(chosenWaypoints.contains(id)){
            chosenWaypoints.remove(id);
        } else chosenWaypoints.add(id);
    }
}
