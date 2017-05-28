package com.bearcave.passageplanning.routes.database.route;


import android.content.ContentValues;
import android.database.Cursor;

import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.base.database.BaseTable;

import java.util.LinkedHashMap;

public class RouteTable extends BaseTable<RouteDAO> implements RouteCRUD {
    public RouteTable(ManagerListener manager) {
        super(manager);
    }

    @Override
    public String getTableName() {
        return "routes";
    }

    @Override
    protected LinkedHashMap<String, String> createKeyToValueTypeHolder() {
        LinkedHashMap<String, String> requestedTypeHolder = new LinkedHashMap<>();
        requestedTypeHolder.put(KEY_ID, INTEGER+PRIMARY_KEY+AUTOINCREMENT);
        requestedTypeHolder.put(KEY_NAME, TEXT + NOT_NULL);
        requestedTypeHolder.put(KEY_WAYPOINTS, DATETIME + NOT_NULL);
        return requestedTypeHolder;
    }

    @Override
    protected ContentValues getContentValue(RouteDAO element) {
        ContentValues value = new ContentValues();
        value.put(KEY_NAME, element.getName());
        value.put(KEY_WAYPOINTS, RouteDAO.fromList(element.getWaypointsIds()));
        return value;
    }

    @Override
    protected RouteDAO loadFrom(Cursor cursor) {
        return new RouteDAO(
                cursor.getInt(0),
                cursor.getString(1),
                RouteDAO.fromString(cursor.getString(2))
        );
    }

    private static final String KEY_NAME = "name";
    private static final String KEY_WAYPOINTS = "waypoints_ids";

}
