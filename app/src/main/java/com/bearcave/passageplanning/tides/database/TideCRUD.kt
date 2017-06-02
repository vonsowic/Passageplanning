package com.bearcave.passageplanning.tides.database

import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey

import org.joda.time.DateTime


interface TideCRUD : CRUDWithCustomKey<TideItem, DateTime> {
    fun insertAll(tides: Collection<TideItem>)
}
