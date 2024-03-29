package com.bearcave.passageplanning.waypoints.database;

import com.bearcave.passageplanning.tides.utils.Gauge;

import org.junit.Test;


public class WaypointTest {

    @Test
    public void castToWaypointTest() throws Exception {
        Waypoint waypointDAO = new Waypoint(1, "humanCode", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        Waypoint waypoint = waypointDAO;
    }

    @Test( expected = ClassCastException.class)
    public void castToWaypointDAOTestTHatDoestork() throws Exception {
        Waypoint waypoint = new Waypoint(0,  "humanCode", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        Waypoint result = (Waypoint) waypoint;
    }

    @Test
    public void castToWaypointDAOTest() throws Exception {
        Waypoint waypoint = new Waypoint(1, "humanCode", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        Waypoint tmp = waypoint;

        Waypoint result = (Waypoint) tmp;
    }

}