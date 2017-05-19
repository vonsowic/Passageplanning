package com.bearcave.passageplanning.data.database.tables.tide;

import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.thames_tide_provider.TideItem;

import org.joda.time.DateTime;



public class TideItemDAO extends TideItem implements DatabaseElement {

    private long id = -1;

    public TideItemDAO(float predictedTideHeight, DateTime time) {
        super(predictedTideHeight, time);
    }

    public TideItemDAO(long id, float predictedTideHeight, DateTime time) {
        super(predictedTideHeight, time);
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }
}
