package com.bearcave.passageplanning.waypoints_view.manager.editor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import java.io.Serializable;

import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity implements GaugeListAdapter.GaugeAdapterListener{

    public static final int WAYPOINT_REQUEST = 1;
    public static final String WAYPOINT_RESULT = "waypoints_result";

    private EditText name;
    private TextInputEditText ukc;
    private EditText longitude;
    private EditText latitude;
    private EditText characteristic;

    private Waypoint waypoint;

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.editor_title));

        Serializable tmp = getIntent().getSerializableExtra(WAYPOINT_RESULT);
        if(tmp == null){
            waypoint = new Waypoint();
        } else {
            waypoint = (Waypoint) tmp;
        }

        findViewById(R.id.save_button).setOnClickListener(v -> onSaveButtonClicked());
        name =          (EditText) findViewById(R.id.name_text);
        characteristic =(EditText) findViewById(R.id.characteristic_text);
        ukc =           (TextInputEditText) findViewById(R.id.ukc_text);
        longitude =     (EditText) findViewById(R.id.longitude_text);
        latitude =      (EditText) findViewById(R.id.latitude_text);

        listView = (ExpandableListView) findViewById(R.id.gauge_list_view);
        listView.setAdapter(new GaugeListAdapter(this, waypoint.getGauge()));
    }

    //@OnClick(R.id.save_button)  Doesnt work
    public void onSaveButtonClicked(){
        if (!isAllFilled()){
            Toast.makeText(this, "Fill all gaps", Toast.LENGTH_SHORT).show();
            return;
        } else updateWaypoint();

        Intent returnIntent = new Intent();
        returnIntent.putExtra(WAYPOINT_RESULT, waypoint);
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

    private void updateWaypoint(){
        waypoint.setName(name.getText().toString());
        waypoint.setCharacteristic(characteristic.getText().toString());
        waypoint.setUkc(Float.valueOf(ukc.getText().toString()));
        waypoint.setLatitude(Double.valueOf(latitude.getText().toString()));
        waypoint.setLongitude(Double.valueOf(longitude.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onBackPressed();
    }

    @Override
    public void onItemSelectedListener(Gauge gauge) {
        this.waypoint.setGauge(gauge);
        //listView.collapseGroup(0);
    }
}
