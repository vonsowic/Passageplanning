package com.bearcave.passageplanning.data.database.tables.base;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bearcave.passageplanning.data.database.ManagerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseTable<T extends DatabaseElement> implements ManagerListener, CRUD<T>, ReadManyDaos<T>{

    private ManagerListener manager;

    public BaseTable(ManagerListener manager) {
        this.manager = manager;
    }

    public abstract String getTableName();

    protected abstract LinkedHashMap<String, String> createKeyToValueTypeHolder();

    protected abstract ContentValues getContentValue(T element);

    protected String getKeyAsString(){
        return KEY_ID;
    }

    protected abstract T loadFrom(Cursor cursor);

    public final String tableCreator(){
        StringBuilder table = new StringBuilder();
        table.append("CREATE TABLE ");
        table.append(getTableName());
        table.append("(");

        for (Map.Entry<String, String> entry : createKeyToValueTypeHolder().entrySet()) {
            table.append(entry.getKey() + " " +  entry.getValue() + "," );
        }

        table.delete(table.length()-1, table.length());
        table.append(");");

        return table.toString();
    }

    private String[] getTableColumns(){
        LinkedHashMap<String, String> typeHolder = createKeyToValueTypeHolder();

        String[] result = new String[typeHolder.size()];

        int i = 0;
        for(String key: typeHolder.keySet()){
            result[i++] = key;
        }

        return result;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return manager.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return manager.getReadableDatabase();
    }

    @Override
    public Integer insert(T element) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValue(element);

        Integer id = (int) db.insert(getTableName(), null, values);

        db.close();
        return id;
    }

    @Override
    public T read(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                getTableName(),
                getTableColumns(),
                getKeyAsString() + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return loadFrom(cursor);
    }

    @Override
    public List<T> readAll() {
        ArrayList<T> requestedList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + getTableName();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                requestedList.add(loadFrom(cursor));
            } while (cursor.moveToNext());
        }

        // return contact list
        return requestedList;
    }

    @Override
    public List<T> read(List<Integer> ids) {
        ArrayList<T> daos = new ArrayList<>();

        for (Integer id: ids){
            daos.add(read(id));
        }

        return daos;
    }

    @Override
    public List<T> readAllDaos() {
        return readAll();
    }

    @Override
    public int update(T element) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentValue(element);

        // updating row
        return db.update(
                getTableName(),
                values,
                getKeyAsString() + " = ?",
                new String[] { String.valueOf(element.getId()) });
    }

    @Override
    public int delete(T element) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                getTableName(),
                getKeyAsString() + " = ?",
                new String[] { String.valueOf(element.getId()) }
        );

        db.close();
        return result;
    }

    protected static final String KEY_ID = " id ";

    protected static final String INTEGER = " INTEGER ";
    protected static final String TEXT = " TEXT ";
    protected static final String FLOAT = " FLOAT";
    protected static final String DOUBLE = " DOUBLE ";
    protected static final String DATETIME = " DATETIME ";


    protected static final String NOT_NULL = " NOT NULL ";
    protected static final String AUTOINCREMENT = " AUTOINCREMENT ";
    protected static final String PRIMARY_KEY = " PRIMARY KEY ";
    protected static final String UNIQUE = " UNIQUE ";
}
