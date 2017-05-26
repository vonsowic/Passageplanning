package com.bearcave.passageplanning.data.database.tables.tide;

import android.content.ContentValues;
import android.database.Cursor;

import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.data.database.tables.base.BaseTable;
import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.BaseTableWithCustomKey;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import org.joda.time.DateTime;

import java.util.LinkedHashMap;


public class TidesTable extends BaseTableWithCustomKey<TideItemDAO, DateTime> implements TideCRUD {

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
    protected ContentValues getContentValue(TideItemDAO element) {
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, element.getTime().toString());
        values.put(KEY_PREDICTED, element.getPredictedTideHeight());
        return values;
    }

    @Override
    protected TideItemDAO loadFrom(Cursor cursor) {
        return new TideItemDAO(
                cursor.getFloat(0),
                DateTime.parse(cursor.getString(1))
        );
    }

    @Override
    protected String getIdKey() {
        return KEY_TIME;
    }

    private static final String KEY_PREDICTED = "predicted_tide_depth";
    private static final String KEY_TIME = "date_time";
}
