package com.bearcave.passageplanning.base.database;


import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey;
import com.bearcave.passageplanning.data.database.ManagerListener;

public abstract class BaseTable<Dao extends DatabaseElement> extends BaseTableWithCustomKey<Dao, Integer>
        implements ManagerListener, CRUD<Dao> {

    public BaseTable(ManagerListener manager) {
        super(manager);
    }

    @Override
    protected String getIdKey(){
        return KEY_ID;
    }
}
