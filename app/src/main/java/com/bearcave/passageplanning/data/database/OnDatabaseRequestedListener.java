package com.bearcave.passageplanning.data.database;

import com.bearcave.passageplanning.data.database.tables.base.BaseTable;

public interface OnDatabaseRequestedListener{
        BaseTable onGetTableListener(int tableId);
    }