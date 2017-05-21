package com.bearcave.passageplanning.data.database;

import android.database.sqlite.SQLiteDatabase;

public interface ManagerListener {
    SQLiteDatabase getWritableDatabase();
    SQLiteDatabase getReadableDatabase();
}
