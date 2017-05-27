package com.bearcave.passageplanning.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey;
import com.bearcave.passageplanning.routes.database.route.RouteCRUD;
import com.bearcave.passageplanning.routes.database.route.RouteTable;
import com.bearcave.passageplanning.thames_tide_provider.database.TidesTable;
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD;
import com.bearcave.passageplanning.waypoints.database.WaypointsTable;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;


public class DatabaseManager extends SQLiteOpenHelper implements ManagerListener {

    private final SparseArray<BaseTableWithCustomKey> tables = new SparseArray<>();

    public DatabaseManager(final Context context, String databaseName) {
        super(new DatabaseContext(context), databaseName, null, VERSION);
        tables.put(WaypointCRUD.ID, new WaypointsTable(this));
        tables.put(RouteCRUD.ID, new RouteTable(this));

        for(Gauge gauge: Gauge.values()){
            tables.put(gauge.getId(), new TidesTable(this, gauge));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < tables.size(); i++) {
            int key = tables.keyAt(i);
            db.execSQL(tables.get(key).tableCreator());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: implement data migration
        for(int i = 0; i < tables.size(); i++) {
            int key = tables.keyAt(i);
            db.execSQL("DROP TABLE IF EXISTS " + tables.get(key).getTableName());
        }

        onCreate(db);
    }


    private static final int VERSION = 19;

    public BaseTableWithCustomKey getTable(int tableId){
        return tables.get(tableId);
    }

}
