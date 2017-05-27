package com.bearcave.passageplanning.base.database;


import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey;

public interface CRUD<T> extends CRUDWithCustomKey<T, Integer> {
}
