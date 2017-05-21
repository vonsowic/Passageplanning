package com.bearcave.passageplanning.data.database.tables.waypoints;

import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;
import com.bearcave.passageplanning.utils.Waypoint;

import org.junit.Test;


public class WaypointDAOTest {

    @Test
    public void castToWaypointTest() throws Exception {
        WaypointDAO waypointDAO = new WaypointDAO(1, "name", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        Waypoint waypoint = waypointDAO;
    }

    @Test( expected = ClassCastException.class)
    public void castToWaypointDAOTestTHatDoestork() throws Exception {
        Waypoint waypoint = new Waypoint( "name", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        WaypointDAO result = (WaypointDAO) waypoint;
    }

    @Test
    public void castToWaypointDAOTest() throws Exception {
        WaypointDAO waypointDAO = new WaypointDAO(1, "name", "note", "characteristic", 0.1f, 0.2, 0.4, Gauge.CHELSEA_BRIDGE);

        Waypoint tmp = waypointDAO;

        WaypointDAO result = (WaypointDAO) tmp;
    }

}