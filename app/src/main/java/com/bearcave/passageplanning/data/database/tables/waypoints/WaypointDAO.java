package com.bearcave.passageplanning.data.database.tables.waypoints;


import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;

import org.jetbrains.annotations.NotNull;

public class WaypointDAO extends com.bearcave.passageplanning.utils.Waypoint implements DatabaseElement {
    private long id = -1;

    @Override
    public long getId() {
        return id;
    }

    public WaypointDAO() {
    }

    public WaypointDAO(long id, @NotNull String name, @NotNull String note, @NotNull String characteristic, float ukc, double latitude, double longitude, @NotNull Gauge gauge) {
        super(name, note, characteristic, ukc, latitude, longitude, gauge);
        this.id = id;
    }

    public WaypointDAO(long id, @NotNull String name, @NotNull String characteristic, float ukc, double latitude, double longitude, @NotNull Gauge gauge) {
        super(name, characteristic, ukc, latitude, longitude, gauge);
        this.id = id;
    }
}
