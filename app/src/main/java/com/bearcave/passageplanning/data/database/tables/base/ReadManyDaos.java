package com.bearcave.passageplanning.data.database.tables.base;

import android.content.Intent;

import com.bearcave.passageplanning.data.database.tables.waypoints.WaypointDAO;

import java.util.List;


public interface ReadManyDaos<Dao> extends ReadManyDaosWithCustomKey<Dao, Integer> {
}
