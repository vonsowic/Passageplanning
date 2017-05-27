package com.bearcave.passageplanning.base.database.withcustomkey;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.bearcave.passageplanning.data.database.ManagerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <Dao> - Data Access Object
 * @param <Id> - Column id in database.
 */
public abstract class BaseTableWithCustomKey<Dao extends DatabaseElementWithCustomKey<Id>, Id>
        implements ManagerListener,
        CRUDWithCustomKey<Dao, Id> {

    private ManagerListener manager;

    public BaseTableWithCustomKey(ManagerListener manager) {
        this.manager = manager;
    }

    public abstract String getTableName();

    protected abstract LinkedHashMap<String, String> createKeyToValueTypeHolder();

    protected abstract ContentValues getContentValue(Dao element);

    protected abstract String getIdKey();

    protected abstract Dao loadFrom(Cursor cursor);

    public final String tableCreator(){
        StringBuilder table = new StringBuilder();
        table.append("CREATE TABLE ");
        table.append(getTableName());
        table.append("(");

        for (Map.Entry<String, String> entry : createKeyToValueTypeHolder().entrySet()) {
            table.append(entry.getKey() + " " +  entry.getValue() + "," );
        }

        for (String[] reference: createReferences()){
            table.append(referenceTemplate(reference[0], reference[1], reference[2]));
        }

        table.delete(table.length()-1, table.length());

        table.append(");");

        return table.toString();
    }

    /**
     * @return ArrayList, which contains 3-dimensional arrays [key id, foreign table name, foreign key id]
     */
    protected ArrayList<String[]> createReferences(){
        return new ArrayList<>();
    }

    @NonNull
    private final String referenceTemplate(String keyId, String table, String foreignKeyId){
        return " FOREIGN KEY ("+keyId+") REFERENCES "+table+"("+foreignKeyId+"),";
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
    public long insert(Dao element) {
        return getWritableDatabase()
                .insert(
                        getTableName(),
                        null,
                        getContentValue(element)
                );
    }

    @Override
    public Dao read(Id id) {
        Cursor cursor = getReadableDatabase().query(
                getTableName(),
                getTableColumns(),
                getIdKey() + ANY,
                new String[] { id.toString() },
                null, null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();

        return loadFrom(cursor);
    }

    @Override
    public List<Dao> readAll() {
        ArrayList<Dao> requestedList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + getTableName();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                requestedList.add(loadFrom(cursor));
            } while (cursor.moveToNext());
        }

        return requestedList;
    }

    @Override
    public int update(Dao element) {
        return getWritableDatabase().update(
                getTableName(),
                getContentValue(element),
                getIdKey() + ANY,
                new String[] { element.getId().toString() }
        );
    }

    @Override
    public int delete(Dao element) {
        return getWritableDatabase().delete(
                getTableName(),
                getIdKey() + ANY,
                new String[] { element.getId().toString() }
        );
    }

    protected static final String KEY_ID = " id ";

    protected static final String ANY = " = ?";

    protected static final String INTEGER = " INTEGER ";
    protected static final String TEXT = " TEXT ";
    protected static final String FLOAT = " FLOAT";
    protected static final String DOUBLE = " DOUBLE ";
    protected static final String DATETIME = " DATETIME ";

    protected static final String NOT_NULL = " NOT NULL ";
    protected static final String AUTOINCREMENT = " AUTOINCREMENT ";
    protected static final String PRIMARY_KEY = " PRIMARY KEY ";
    protected static final String UNIQUE = " UNIQUE ";
    protected static final String REFERENCE = " REFERENCES ";
    protected static final String FOREIGN_KEY = " FOREIGN KEY ";


}
