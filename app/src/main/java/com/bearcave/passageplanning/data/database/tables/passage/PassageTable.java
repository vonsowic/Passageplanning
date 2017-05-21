package com.bearcave.passageplanning.data.database.tables.passage;


import android.content.ContentValues;
import android.database.Cursor;

import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.data.database.tables.base.BaseTable;

import java.util.LinkedHashMap;

public class PassageTable extends BaseTable<PassageDAO> implements PassageCRUD {
    public PassageTable(ManagerListener manager) {
        super(manager);
    }

    @Override
    public String getTableName() {
        return "routes";
    }

    @Override
    protected LinkedHashMap<String, String> createKeyToValueTypeHolder() {
        LinkedHashMap<String, String> requestedTypeHolder = new LinkedHashMap<>();
        //requestedTypeHolder.put(KEY_ID, INTEGER+PRIMARY_KEY+NOT_NULL+AUTOINCREMENT);
        requestedTypeHolder.put(KEY_NAME, TEXT + PRIMARY_KEY + NOT_NULL);
        requestedTypeHolder.put(KEY_WAYPOINTS, DATETIME + NOT_NULL);
        return requestedTypeHolder;
    }

    @Override
    protected ContentValues getContentValue(PassageDAO element) {
        ContentValues value = new ContentValues();
        //value.put(KEY_ID, element.getId());
        value.put(KEY_NAME, element.getName());
        value.put(KEY_WAYPOINTS, element.toString());
        return value;
    }

    @Override
    protected PassageDAO loadFrom(Cursor cursor) {
        return new PassageDAO(
                cursor.getInt(0),
                cursor.getString(1),
                PassageDAO.parse(cursor.getString(2))
        );
    }

    @Override
    protected String getKeyAsString() {
        return KEY_NAME;
    }

    private static final String KEY_NAME = "name";
    private static final String KEY_WAYPOINTS = "waypoints_ids";

}
