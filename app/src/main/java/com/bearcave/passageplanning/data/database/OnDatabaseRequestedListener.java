package com.bearcave.passageplanning.data.database;

import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey;

public interface OnDatabaseRequestedListener{
        BaseTableWithCustomKey onGetTableListener(int tableId);
    }