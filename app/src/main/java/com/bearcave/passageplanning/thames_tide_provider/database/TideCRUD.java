package com.bearcave.passageplanning.thames_tide_provider.database;

import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey;

import org.joda.time.DateTime;


public interface TideCRUD extends CRUDWithCustomKey<TideItemDAO, DateTime> {
}
