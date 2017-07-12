package com.bearcave.passageplanning.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey;
import com.bearcave.passageplanning.passages.database.AccessToRouteTable;
import com.bearcave.passageplanning.passages.database.PassageCRUD;
import com.bearcave.passageplanning.passages.database.PassageTable;
import com.bearcave.passageplanning.routes.database.Route;
import com.bearcave.passageplanning.routes.database.RouteCRUD;
import com.bearcave.passageplanning.routes.database.RouteTable;
import com.bearcave.passageplanning.tides.database.TidesTable;
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge;
import com.bearcave.passageplanning.waypoints.database.WaypointCRUD;
import com.bearcave.passageplanning.waypoints.database.WaypointsTable;

import org.jetbrains.annotations.NotNull;


/**
 * DO NOT CONVERT THIS CLASS TO KOTLIN!
 * This would lead to platform declaration clash, because of the
 * {@see com.bearcave.passageplanning.data.database.ManagerLister} and {@see SQLiteOpenHelper}
 * database getters.
 */
public class DatabaseManager extends SQLiteOpenHelper implements ManagerListener, AccessToRouteTable {

    private final SparseArray<BaseTableWithCustomKey> tables = new SparseArray<>();

    public DatabaseManager(final Context context, String databaseName) {
        super(new DatabaseContext(context), databaseName, null, VERSION);
        tables.put(WaypointCRUD.Companion.getID(), new WaypointsTable(this));
        tables.put(RouteCRUD.Companion.getID(), new RouteTable(this));
        tables.put(PassageCRUD.Companion.getID(), new PassageTable(this));

        for(Gauge gauge: Gauge.values()){
            tables.put(gauge.getId(), new TidesTable(this, gauge));
        }

        DATABASE_MANAGER = this;
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
        for(int i = 0; i < tables.size(); i++) {
            int key = tables.keyAt(i);
            db.execSQL("DROP TABLE IF EXISTS " + tables.get(key).getTableName());
        }

        onCreate(db);
    }


    private static final int VERSION = 24;

    public BaseTableWithCustomKey getTable(int tableId){
        return tables.get(tableId);
    }

    @NotNull
    @Override
    public Route readRoute(int id) {
        return ((RouteTable) tables.get(RouteCRUD.Companion.getID())).read(id);
    }

    public static DatabaseManager DATABASE_MANAGER = null;
}