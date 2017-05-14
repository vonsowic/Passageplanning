package com.bearcave.passageplanning.waypoints_view.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.waypoints.Waypoint;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorActivity extends AppCompatActivity {

    public static final int WAYPOINT_REQUEST = 1;
    public static final String WAYPOINT_RESULT = "waypoints_result";

    @BindView(R.id.name_text)           EditText name;
    @BindView(R.id.ukc_text)            EditText ukc;
    @BindView(R.id.longitude_text)      EditText longitude;
    @BindView(R.id.latitude_text)       EditText latitude;
    @BindView(R.id.characteristic_text) EditText characteristic;

    private Waypoint waypoint;

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
    }

    //@OnClick(R.id.save_button)  Doesnt work
    public void onSaveButtonClicked(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra(WAYPOINT_RESULT, waypoint);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onBackPressed();
    }
}
