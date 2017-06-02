package com.bearcave.passageplanning.data.database;


import android.database.sqlite.SQLiteDatabase;

/**
 * This is used to get access to database by BaseTable classes.
 */
public interface ManagerListener {
    SQLiteDatabase getWritableDatabase();
    SQLiteDatabase getReadableDatabase();
}
