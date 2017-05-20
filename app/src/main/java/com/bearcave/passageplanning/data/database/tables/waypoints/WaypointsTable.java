package com.bearcave.passageplanning.data.database.tables.waypoints;


import android.content.ContentValues;
import android.database.Cursor;

import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.data.database.tables.base.BaseTable;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import java.util.LinkedHashMap;


public class WaypointsTable extends BaseTable<WaypointDAO> {

    public WaypointsTable(ManagerListener manager) {
        super(manager);
    }

    @Override
    public String getTableName() {
        return "waypoints";
    }

    @Override
    protected LinkedHashMap<String, String> getKeyToValueTypeHolder() {
        LinkedHashMap<String, String> typeHolder = new LinkedHashMap<>();

        typeHolder.put(KEY_ID, INTEGER+PRIMARY_KEY+AUTOINCREMENT);
        typeHolder.put(KEY_NAME, TEXT+NOT_NULL);
        typeHolder.put(KEY_NOTE, TEXT);
        typeHolder.put(KEY_CHARACTERISTIC, TEXT+NOT_NULL);
        typeHolder.put(KEY_UKC, FLOAT+NOT_NULL);
        typeHolder.put(KEY_LATITUDE, DOUBLE+NOT_NULL);
        typeHolder.put(KEY_LONGITUDE, DOUBLE+NOT_NULL);
        typeHolder.put(KEY_GAUGE, INTEGER+NOT_NULL);

        return typeHolder;
    }


    @Override
    protected ContentValues getContentValue(WaypointDAO waypoint) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, waypoint.getId());
        values.put(KEY_NAME, waypoint.getName());
        values.put(KEY_NOTE, waypoint.getNote());
        values.put(KEY_CHARACTERISTIC, waypoint.getCharacteristic());
        values.put(KEY_UKC, waypoint.getUkc());
        values.put(KEY_LATITUDE, waypoint.getLatitude());
        values.put(KEY_LONGITUDE, waypoint.getLongitude());
        values.put(KEY_GAUGE, waypoint.getGauge().getId());
        return values;
    }

    @Override
    protected WaypointDAO loadFrom(Cursor cursor) {
        return new WaypointDAO(
                cursor.getInt(0),               // id
                cursor.getString(1),            // name
                cursor.getString(2),            // note
                cursor.getString(3),            // characteristic
                cursor.getFloat(4),             // ukc
                cursor.getDouble(5),            // latitude
                cursor.getDouble(6),            // longitude
                Gauge.getById(cursor.getInt(7)) // gauge
        );
    }

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CHARACTERISTIC = "characteristic";
    private static final String KEY_NOTE = "note";
    private static final String KEY_UKC = "ukc";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_GAUGE = "gauge_id";
}
