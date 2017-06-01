package com.bearcave.passageplanning.data.database

import android.database.sqlite.SQLiteDatabase

interface ManagerListener {
    val writableDatabase: SQLiteDatabase
    val readableDatabase: SQLiteDatabase
}
