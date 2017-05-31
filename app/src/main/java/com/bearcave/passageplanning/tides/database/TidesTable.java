package com.bearcave.passageplanning.tides.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey;
import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.LinkedHashMap;


public class TidesTable extends BaseTableWithCustomKey<TideItem, DateTime> implements TideCRUD {

    private String gauge;

    public TidesTable(ManagerListener manager, Gauge gauge) {
        super(manager);
        this.gauge = gauge.getName();
    }

    @Override
    public String getTableName() {
        return gauge.replace(" ", "_");
    }


    @Override
    protected LinkedHashMap<String, String> createKeyToValueTypeHolder() {
        LinkedHashMap<String, String> requestedTypeHolder = new LinkedHashMap<>();
        requestedTypeHolder.put(KEY_TIME, DATETIME + PRIMARY_KEY + NOT_NULL);
        requestedTypeHolder.put(KEY_PREDICTED, FLOAT + NOT_NULL);
        return requestedTypeHolder;
    }

    @Override
    protected ContentValues getContentValue(TideItem element) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, element.getId().toString());
        values.put(KEY_PREDICTED, element.getPredictedTideHeight());
        return values;
    }

    @Override
    protected TideItem loadFrom(Cursor cursor) {
        return new TideItem(
                DateTime.parse(cursor.getString(0)),
                cursor.getFloat(1)
                );
    }

    @Override
    public void insertAll(Collection<TideItem> tides){
        SQLiteDatabase database = getWritableDatabase();

        for(TideItem item: tides){
            database.insert(
                    getTableName(),
                    null,
                    getContentValue(item)
            );
        }
    }

    @Override
    protected String getIdKey() {
        return KEY_TIME;
    }

    private static final String KEY_PREDICTED = "predicted_tide_depth";
    private static final String KEY_TIME = "date_time";
}
