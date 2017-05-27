package com.bearcave.passageplanning.base.database.withcustomkey;


public interface DatabaseElementWithCustomKey<Id> {
    Id getId();
    String getName();
}
