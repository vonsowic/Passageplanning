package com.bearcave.passageplanning.passages;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.data.database.tables.passage.PassageDAO;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.ArrayList;


public class PassageEditorActivity extends BaseEditorActivity<PassageDAO> {

    private EditText name;
    private TextView waypointChooser;
    private ArrayList<Integer> waypointsIds = new ArrayList<>();
    private ArrayList<WaypointDAO> waypoints = new ArrayList<>();

    // TODO: get all waypoints

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = (EditText) findViewById(R.id.name_text);

        waypointChooser = (TextView) findViewById(R.id.open_waypoints_chooser);
        registerForContextMenu(waypointChooser);
        waypointChooser.setOnClickListener(this::openContextMenu);
    }

    @Override
    protected void setViewsContent(PassageDAO passage) {
        name.setText(passage.getName());
        waypointsIds = passage.getWaypointsIds();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.content_passage_editor;
    }

    @Override
    public boolean isAllFilled() {
        return name.getText().length() != 0 && waypointsIds.size() != 0;
    }

    @Override
    protected PassageDAO getFilledDAO() {
        return new PassageDAO(
                name.getText().toString(),
                waypointsIds
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.choose_waypoints);

        for(WaypointDAO waypoint: waypoints) {
            menu.add(Menu.NONE, (int) waypoint.getId(), Menu.NONE, waypoint.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (waypointsIds.contains(id)){
            waypointsIds.remove(id);
        } else waypointsIds.add(id);

        return super.onOptionsItemSelected(item);
    }
}
