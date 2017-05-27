package com.bearcave.passageplanning.routes;

import com.bearcave.passageplanning.waypoints.database.WaypointDAO;

import java.util.List;

/**
 * Created by miwas on 24.05.17.
 */

public interface ReadWaypoints {
    List<WaypointDAO> readAllWaypoints();
}
