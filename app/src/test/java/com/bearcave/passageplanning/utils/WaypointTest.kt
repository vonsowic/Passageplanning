package com.bearcave.passageplanning.utils

import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.waypoints.database.Waypoint
import org.junit.Assert
import org.junit.Test


class WaypointTest {

    val wpt = Waypoint(
            1,
            "Oaze Deep",
            "",
            "Fl(2) G 5s",
            16.5F,
            51.5085300,
            -0.1257400,
            Gauge.CORYTON
    )


    @Test
    fun bearingToNorth() {
        val wptNorth = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1.0,
                wpt.longitude,
                Gauge.CORYTON
        )

        Assert.assertEquals(0.0f, wpt.bearingTo(wptNorth))
    }

    @Test
    fun bearingToSouth() {
        val wptSouth = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1,
                wpt.longitude,
                Gauge.CORYTON
        )

        Assert.assertEquals(180.0f, wpt.bearingTo(wptSouth))
    }

    @Test
    fun bearingToEast() {
        val wptEast = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude,
                wpt.longitude+1.0,
                Gauge.CORYTON
        )

        Assert.assertEquals(90.0f, wpt.bearingTo(wptEast))
    }

    @Test
    fun bearingToWest() {
        val wptWest = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude,
                wpt.longitude-1.0,
                Gauge.MARGATE
        )

        Assert.assertEquals(270.0f, wpt.bearingTo(wptWest))
    }

    @Test
    fun bearingToNorthWest() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1.0,
                wpt.longitude-1.0,
                Gauge.CORYTON
        )

        Assert.assertEquals(315.0f, wpt.bearingTo(wpt2))
    }

    @Test
    fun bearingToSouthWest() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1.0,
                wpt.longitude-1.0,
                Gauge.CORYTON
        )

        Assert.assertEquals(225.0f, wpt.bearingTo(wpt2))
    }


    @Test
    fun bearingToSouthEast() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1.0,
                wpt.longitude+1.0,
                Gauge.CORYTON
        )

        Assert.assertEquals(135.0f, wpt.bearingTo(wpt2))
    }

    @Test
    fun bearingToNorthEast() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1.0,
                wpt.longitude+1.0,
                Gauge.CORYTON
        )

        Assert.assertEquals(45.0f, wpt.bearingTo(wpt2))
    }


}
