package com.bearcave.passageplanning.data.database.tables.tide;

import com.bearcave.passageplanning.data.database.tables.base.CRUD;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.CRUDWithCustomKey;

import org.joda.time.DateTime;


public interface TideCRUD extends CRUDWithCustomKey<TideItemDAO, DateTime> {
}
