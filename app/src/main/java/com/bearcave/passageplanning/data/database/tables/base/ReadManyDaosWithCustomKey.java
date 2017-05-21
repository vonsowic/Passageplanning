package com.bearcave.passageplanning.data.database.tables.base;

import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.List;


public interface ReadManyDaosWithCustomKey<Dao, K> {
    List<Dao> read(List<K> ids);
    List<Dao> readAllDaos();
}
