package com.bearcave.passageplanning.data.database;

import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.BaseTableWithCustomKey;

public interface OnDatabaseRequestedListener{
        BaseTableWithCustomKey onGetTableListener(int tableId);
    }