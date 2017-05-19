package com.bearcave.passageplanning.main_activity.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bearcave.passageplanning.waypoints.Waypoint;
import com.bearcave.passageplanning.waypoints.Waypoints;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import java.util.List;


public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(final Context context, String databaseName) {
        super(new DatabaseContext(context), databaseName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WAYPOINTS_TABLE = "CREATE TABLE " + WAYPOINTS_TABLE + "("
                + KEY_ID                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME              + " TEXT NOT NULL,"
                + KEY_CHARACTERISTIC    + " TEXT NOT NULL,"
                + KEY_UKC               + " FLOAT,"
                + KEY_LONGITUDE         + " DOUBLE,"
                + KEY_LATITUDE          + " DOUBLE"
                + KEY_GAUGE             + " INTEGER"
                + ");";
        db.execSQL(CREATE_WAYPOINTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WAYPOINTS_TABLE);
        onCreate(db);
    }

    public long addWaypoint(Waypoint waypoint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getWaypointValue(waypoint);

        long id = db.insert(WAYPOINTS_TABLE, null, values);

        db.close();
        return id;
    }

    public Waypoint getWaypoint(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                WAYPOINTS_TABLE,
                new String[] { KEY_ID, KEY_NAME, KEY_CHARACTERISTIC, KEY_UKC, KEY_LONGITUDE, KEY_LATITUDE },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Waypoint waypoint = loadWaypoint(cursor);

        return waypoint;
    }

    private Waypoint loadWaypoint(Cursor cursor){
        return new Waypoint(
                Integer.parseInt(cursor.getString(0)),  // id
                cursor.getString(1),                    // name
                cursor.getString(2),                    // characteristic
                Float.valueOf(cursor.getString(3)),     // ukc
                Double.valueOf(cursor.getString(4)),    // longitude
                Double.valueOf(cursor.getString(5)),    // latitude
                Gauge.getById(Integer.valueOf(cursor.getString(6)))     // latitude
        );
    }

    private ContentValues getWaypointValue(Waypoint waypoint){
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, waypoint.getName());
        values.put(KEY_CHARACTERISTIC, waypoint.getCharacteristic());
        values.put(KEY_UKC, waypoint.getUkc());
        values.put(KEY_LONGITUDE, waypoint.getLongitude());
        values.put(KEY_LATITUDE, waypoint.getLatitude());
        return values;
    }

    public List<Waypoint> getAllWaypoints(){
        Waypoints waypointsList = new Waypoints();
        String selectQuery = "SELECT  * FROM " + WAYPOINTS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                waypointsList.add(loadWaypoint(cursor));
            } while (cursor.moveToNext());
        }

        // return contact list
        return waypointsList;
    }

    public int updateWaypoint(Waypoint waypoint){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getWaypointValue(waypoint);

        // updating row
        return db.update(WAYPOINTS_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(waypoint.getId()) });
    }

    public int deleteWaypoint(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(WAYPOINTS_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(id) }
        );
        db.close();

        return result;
    }

    public int deleteWaypoint(Waypoint waypoint){
        return this.deleteWaypoint(waypoint.getId());
    }

    private static final int VERSION = 8;

    private static final String WAYPOINTS_TABLE = "waypoints";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CHARACTERISTIC = "characteristic";
    private static final String KEY_UKC = "ukc";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_GAUGE = "gauge_id";
}
