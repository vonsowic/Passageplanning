package com.bearcave.passageplanning.data.database.tables.base;


import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.CRUDWithCustomKey;

public interface CRUD<T> extends CRUDWithCustomKey<T, Integer> {
}
