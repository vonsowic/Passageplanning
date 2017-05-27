package com.bearcave.passageplanning.waypoints;

import android.support.design.widget.TextInputEditText;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.waypoints.database.WaypointDAO;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import butterknife.ButterKnife;

public class WaypointEditorActivity extends BaseEditorActivity<WaypointDAO> {

    private TextInputEditText name;
    private TextInputEditText note;
    private TextInputEditText ukc;
    private TextInputEditText longitude;
    private TextInputEditText latitude;
    private TextInputEditText characteristic;
    private TextView gauge;

    private Integer id = -2;

    @Override
    protected void findViews() {
        super.findViews();
        name =              ButterKnife.findById(this, R.id.name_text);
        note =              ButterKnife.findById(this, R.id.note_text);
        characteristic =    ButterKnife.findById(this, R.id.characteristic_text);
        ukc =               ButterKnife.findById(this, R.id.ukc_text);
        latitude =          ButterKnife.findById(this, R.id.latitude_text);
        longitude =         ButterKnife.findById(this, R.id.longitude_text);
        gauge =             ButterKnife.findById(this, R.id.gauge_name);
        gauge.setText(Gauge.MARGATE.getName());

        registerForContextMenu(gauge);
        gauge.setOnClickListener(this::openContextMenu);
    }

    @Override
    protected void setViewsContent(WaypointDAO waypoint) {
        id = waypoint.getId();
        name.setText(waypoint.getName());
        note.setText(waypoint.getNote());
        characteristic.setText(waypoint.getCharacteristic());
        ukc.setText(String.valueOf(waypoint.getUkc()));
        latitude.setText(waypoint.getLatitudeInSecondFormat());
        longitude.setText(waypoint.getLongitudeInSecondFormat());
        gauge.setText(waypoint.getGauge().getName());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.content_waypoint_editor;
    }


    public boolean isAllFilled(){
        if( name.getText().length() == 0
            || ukc.getText().length() == 0
            || latitude.getText().length() == 0
            || longitude.getText().length() == 0)
            return false;
        else return true;
    }

    @Override
    protected WaypointDAO getFilledDAO() {
        return new WaypointDAO(
                id,
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
