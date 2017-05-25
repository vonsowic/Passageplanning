package com.bearcave.passageplanning.data.database.tables.base;



import com.bearcave.passageplanning.data.database.ManagerListener;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.BaseTableWithCustomKey;

public abstract class BaseTable<Dao extends DatabaseElement> extends BaseTableWithCustomKey<Dao, Integer>
        implements ManagerListener, CRUD<Dao> {

    public BaseTable(ManagerListener manager) {
        super(manager);
    }
}
