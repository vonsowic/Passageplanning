package com.bearcave.passageplanning.tides.database;

import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey;

import org.joda.time.DateTime;

import java.util.Collection;


public interface TideCRUD extends CRUDWithCustomKey<TideItem, DateTime> {
    void insertAll(Collection<TideItem> tides);
}
