package com.bearcave.passageplanning.data;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class FilesManager {
    private final String DIRECTORY ;
    private final String DATABASE = "database.db";

    private final Context context;


    public FilesManager(Context context) {
        this.context = context;
        DIRECTORY = "/Android/data/" + context.getPackageName();
        createDatabase();
    }

    private File getDirectory(String path){
        File folder = new File(Environment.getExternalStorageDirectory(), path);
        if (!folder.exists()) {
            if(folder.mkdirs()){
                Toast.makeText(context, "Created", Toast.LENGTH_LONG).show();
            } else Toast.makeText(context, "Not created", Toast.LENGTH_LONG).show();

        } else Toast.makeText(context, "Already exists", Toast.LENGTH_LONG).show();

        return folder;
    }

    private DatabaseManager createDatabase(){
        return new DatabaseManager(context, DIRECTORY + File.separator + DATABASE);
    }
}
