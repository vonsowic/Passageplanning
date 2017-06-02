package com.bearcave.passageplanning.data


import android.content.Context
import com.bearcave.passageplanning.data.database.DatabaseManager


class FilesManager(private val context: Context) {
    //private val DIRECTORY: String = Environment.getExternalStorageDirectory().toString() + File.separator + "Android/data" + File.separator + context.packageName
    private val DATABASE = "database.db"


    init {
        createDatabase()
    }

    fun createDatabase(): DatabaseManager {
        return DatabaseManager(context, DATABASE)
    }
}
