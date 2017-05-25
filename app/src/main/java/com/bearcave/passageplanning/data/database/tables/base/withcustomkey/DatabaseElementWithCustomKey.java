package com.bearcave.passageplanning.data.database.tables.base.withcustomkey;


public interface DatabaseElementWithCustomKey<Id> {
    Id getId();
    String getName();
}
