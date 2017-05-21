package com.bearcave.passageplanning.routes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.data.database.tables.route.RouteDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.utils.Waypoint;

import java.util.ArrayList;


public class RouteEditorActivity extends BaseEditorActivity<RouteDAO> {

    private EditText name;
    private TextView waypointChooser;
    private ArrayList<Integer> chosenWaypoints = new ArrayList<>();
    private ArrayList<WaypointDAO> waypoints = new ArrayList<>();

    private long routeId = -2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = (EditText) findViewById(R.id.name_text);

        waypointChooser = (TextView) findViewById(R.id.open_waypoints_chooser);
        registerForContextMenu(waypointChooser);
        waypointChooser.setOnClickListener(this::openContextMenu);
    }

    @Override
    protected void getParcelableExtra(Intent intent) {
        waypoints = intent.getParcelableArrayListExtra(WAYPOINTS_KEY);
    }

    @Override
    protected void setViewsContent(RouteDAO passage) {
        routeId = passage.getId();
        name.setText(passage.getName());
        chosenWaypoints = passage.getWaypointsIds();
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
                chosenWaypoints
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.choose_waypoints);
        menu.setGroupCheckable(Menu.NONE, true, false);

        for(WaypointDAO waypoint: waypoints) {
            menu.add(Menu.NONE, (int) waypoint.getId(), Menu.NONE, waypoint.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (chosenWaypoints.contains(id)){
            chosenWaypoints.remove(id);
        } else chosenWaypoints.add(id);

        return super.onOptionsItemSelected(item);
    }

    public static final String WAYPOINTS_KEY = "waypoints_from_database";
}
