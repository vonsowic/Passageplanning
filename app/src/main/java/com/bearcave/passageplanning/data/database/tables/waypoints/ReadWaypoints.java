package com.bearcave.passageplanning.data.database.tables.waypoints;

import java.util.List;

/**
 * Created by miwas on 20.05.17.
 */

public interface ReadWaypoints {
    List<WaypointDAO> read(List<Integer> ids);
}
