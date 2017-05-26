package com.bearcave.passageplanning.data.database.tables.tide;

import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.DatabaseElementWithCustomKey;
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
