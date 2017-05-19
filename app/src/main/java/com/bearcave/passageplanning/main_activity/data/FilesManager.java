package com.bearcave.passageplanning.main_activity.data;


import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FilesManager {
    private final String DIRECTORY ;
    private final String DATABASE = "database.db";

    private final Context context;


    public FilesManager(Context context) {
        this.context = context;
        DIRECTORY = Environment.getExternalStorageDirectory() + File.separator + "Android/data"+ File.separator + context.getPackageName();
//        context.getDir(DIRECTORY, Context.MODE_PRIVATE);
        createDatabase();
    }

    public DatabaseManager createDatabase(){
        return new DatabaseManager(context,  DATABASE);
    }
}
