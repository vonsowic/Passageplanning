package com.bearcave.passageplanning.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by miwas on 12.05.17.
 */

public class DatabaseContext extends ContextWrapper {

    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DatabaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        File internalStorageFile = Environment.getExternalStorageDirectory();
        String dbFile = internalStorageFile.getAbsolutePath() + File.separator + name;
        if (!dbFile.endsWith(".db")) {
            dbFile += ".db" ;
        }

        File result = new File(dbFile);
        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }


        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT, "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
        }

        return result;
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
