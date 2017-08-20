package com.bearcave.passageplanning.data.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @author Michał Wąsowicz
 * @since 12.05.17.
 * @version 1.0
 */

public class DatabaseContext extends ContextWrapper {

    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        String dbFile = Environment.getExternalStorageDirectory()
                + File.separator + "Android/data"
                + File.separator + getPackageName()
                + File.separator + name;

        if (!dbFile.endsWith(".db")) {
            dbFile += ".db" ;
        }

        File result = new File(dbFile);
        if (!result.getParentFile().exists()) {
            if(result.getParentFile().mkdirs()){
                Log.e(DEBUG_CONTEXT, result.getParentFile() + " has been created");
            } else {
                Log.e(DEBUG_CONTEXT, result.getParentFile() + " has not been created");

            }
        }

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT, "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
        }

        return result;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory)    {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)){
            Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + name + ",,) = " + result.getPath());
        }

        return result;
    }
}