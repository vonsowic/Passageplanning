package com.bearcave.passageplanning.thames_tide_provider.database;

import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey;
import com.bearcave.passageplanning.thames_tide_provider.TideItem;

import org.joda.time.DateTime;



public class TideItemDAO extends TideItem implements DatabaseElementWithCustomKey<DateTime> {

    public TideItemDAO(float predictedTideHeight, DateTime time) {
        super(predictedTideHeight, time);
    }

    @Override
    public DateTime getId() {
        return this.getTime();
    }

    @Override
    public String getName() {
        return this.getTime().toString();
    }
}
