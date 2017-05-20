package com.bearcave.passageplanning.waypoints_manager.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.utils.Waypoint;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import org.w3c.dom.Text;

import java.io.Serializable;

import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    public static final int WAYPOINT_REQUEST = 1;
    public static final String WAYPOINT_RESULT = "waypoints_result";

    private EditText name;
    private EditText note;
    private EditText ukc;
    private EditText longitude;
    private EditText latitude;
    private EditText characteristic;
    private TextView gauge;

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.editor_title));

        Serializable tmp = getIntent().getSerializableExtra(WAYPOINT_RESULT);

        Waypoint waypoint = null;
        if(tmp != null)
            waypoint = (Waypoint) tmp;

        findViewById(R.id.save_button).setOnClickListener(v -> onSaveButtonClicked());
        name =          (EditText) findViewById(R.id.name_text);
        note =          (EditText) findViewById(R.id.note_text);
        characteristic =(EditText) findViewById(R.id.characteristic_text);
        ukc =           (EditText) findViewById(R.id.ukc_text);
        longitude =     (EditText) findViewById(R.id.longitude_text);
        latitude =      (EditText) findViewById(R.id.latitude_text);
        gauge =         (TextView) findViewById(R.id.gauge_name);

        registerForContextMenu(gauge);
        gauge.setOnClickListener(v -> openContextMenu(v));

        if(waypoint != null){
            name.setText(waypoint.getName());
            characteristic.setText(waypoint.getCharacteristic());

            gauge.setText(waypoint.getGauge().getName());
        } else {
            gauge.setText(Gauge.MARGATE.getName());
        }
    }

    //@OnClick(R.id.save_button)  Doesnt work
    public void onSaveButtonClicked(){
        if (!isAllFilled()){
            Toast.makeText(this, "Fill all gaps", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(WAYPOINT_RESULT, getFilledWaypoint());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public boolean isAllFilled(){
        if(name.getText().length() == 0
            || characteristic.getText().length() == 0
            ||ukc.getText().length() == 0
            ||latitude.getText().length() == 0
            ||longitude.getText().length() == 0)
            return false;
        else return true;
    }

    private WaypointDAO getFilledWaypoint(){
        return new WaypointDAO(
                name.getText().toString(),
                note.getText().toString(),
                characteristic.getText().toString(),
                Float.valueOf(ukc.getText().toString()),
                latitude.getText().toString(),
                longitude.getText().toString(),
                Gauge.getByName(gauge.getText().toString())
        );
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.editor_gauge_chooser_title);

        for( Gauge gauge: Gauge.values()){
            menu.add(Menu.NONE, gauge.getId(), Menu.NONE, gauge.getName());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        gauge.setText(Gauge.getById(item.getItemId()).getName());
        return super.onContextItemSelected(item);
    }
}
