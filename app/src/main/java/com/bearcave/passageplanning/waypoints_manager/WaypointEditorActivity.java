package com.bearcave.passageplanning.waypoints_manager;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseEditorActivity;
import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

public class WaypointEditorActivity extends BaseEditorActivity<WaypointDAO> {

    private EditText name;
    private EditText note;
    private EditText ukc;
    private EditText longitude;
    private EditText latitude;
    private EditText characteristic;
    private TextView gauge;

    private Integer id = -2;

    @Override
    protected void findViews() {
        super.findViews();
        name =          (EditText) findViewById(R.id.name_text);
        note =          (EditText) findViewById(R.id.note_text);
        characteristic =(EditText) findViewById(R.id.characteristic_text);
        ukc =           (EditText) findViewById(R.id.ukc_text);
        latitude =      (EditText) findViewById(R.id.latitude_text);
        longitude =     (EditText) findViewById(R.id.longitude_text);
        gauge =         (TextView) findViewById(R.id.gauge_name);
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
