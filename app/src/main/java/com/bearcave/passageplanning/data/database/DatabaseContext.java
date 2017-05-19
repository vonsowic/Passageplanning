package com.bearcave.passageplanning.data.database;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

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
                Toast.makeText(getBaseContext(), result.getParentFile() + " has been created", Toast.LENGTH_LONG).show();
                Log.e(DEBUG_CONTEXT, result.getParentFile() + " has been created");
            } else {
                Toast.makeText(getBaseContext(), result.getParentFile() + " has not been created", Toast.LENGTH_LONG).show();
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

    private boolean checkPermission(){
        return ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory)    {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)){
            Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + name + ",,) = " + result.getPath());
        }
        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

        return result;
    }
}
