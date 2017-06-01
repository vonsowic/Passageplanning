package com.bearcave.passageplanning.data.database

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast

import java.io.File

/**
 * @author Michał Wąsowicz
 * @since  12.05.17.
 * @version 1.0
 */

class DatabaseContext(base: Context) : ContextWrapper(base) {

    override fun getDatabasePath(name: String): File {
        var dbFile = "${Environment.getExternalStorageDirectory().absolutePath}${File.separator}Android/data${File.separator}$packageName${File.separator}$name"

        if (!dbFile.endsWith(".db")) {
            dbFile += ".db"
        }

        val result = File(dbFile)
        if (!result.parentFile.exists()) {
            if (result.parentFile.mkdirs()) {
                Log.e(DEBUG_CONTEXT, "${result.parentFile}  has been created")
            } else {
                Toast.makeText(baseContext, "${result.parentFile} has not been created", Toast.LENGTH_LONG).show()
                Log.e(DEBUG_CONTEXT, "${result.parentFile} has not been created")
            }
        }

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT, "getDatabasePath($name) = ${result.absolutePath}")
        }

        return result
    }

    override fun openOrCreateDatabase(name: String, mode: Int, factory: SQLiteDatabase.CursorFactory, errorHandler: DatabaseErrorHandler?): SQLiteDatabase {
        return openOrCreateDatabase(name, mode, factory)
    }

    private fun checkPermission(): Boolean = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED


    override fun openOrCreateDatabase(name: String, mode: Int, factory: SQLiteDatabase.CursorFactory): SQLiteDatabase {
        val result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null)

        if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
            Log.w(DEBUG_CONTEXT, "openOrCreateDatabase($name) = ${result.path}")
        }
        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

        return result
    }

    companion object {

        private val DEBUG_CONTEXT = "DatabaseContext"
    }
}
