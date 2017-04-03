package com.bearcave.passageplanning.waypoints

import android.location.Location
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by miwas on 12.03.17.
 */
class WaypointTest {

    val el =  """<waypoint id="1" name="Oaze Deep" longitude="51.5085300" latitude="-0.1257400" ukc="16.5" characteristic="Fl(2) G 5s" />"""

    val wpt = Waypoint(
            1,
            "Oaze Deep",
            "Fl(2) G 5s",
            16.5F,
            51.5085300,
            -0.1257400
    )

    @Test
    fun constructorWithJsoupStringParameterTest() {
        val w = Waypoint(el)
        Assert.assertEquals(1, w.id)
        Assert.assertEquals(16.5F, w.ukc)
        Assert.assertEquals("Oaze Deep", w.name)
        Assert.assertEquals(51.5085300, w.longitude, 0.000001)
        Assert.assertEquals(-0.1257400, w.latitude, 0.000001)
        Assert.assertEquals("Fl(2) G 5s", w.characteristic)
    }

    @Test
    fun toXmlElementTest() {
        print(wpt.toXml())
        //Assert.assertEquals(Jsoup.parse(el).body().children().first(), wpt.toXmlElement())
    }

    @Test
    fun toXmlStringTest() {
        print(wpt.toXml())
    }

    @Test
    fun bearingToNorth() {
        val wptNorth = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1,
                wpt.longitude
        )

        Assert.assertEquals(0.0, wpt.bearingTo(wptNorth))
    }

    @Test
    fun bearingToSouth() {
        val wptSouth = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1,
                wpt.longitude
        )

        Assert.assertEquals(180.0, wpt.bearingTo(wptSouth))
    }

    @Test
    fun bearingToEast() {
        val wptEast = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude,
                wpt.longitude+1.0
        )

        Assert.assertEquals(90.0, wpt.bearingTo(wptEast))
    }

    @Test
    fun bearingToWest() {
        val wptWest = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude,
                wpt.longitude-1.0
        )

        Assert.assertEquals(270.0, wpt.bearingTo(wptWest))
    }

    @Test
    fun bearingToNorthWest() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1.0,
                wpt.longitude-1.0
        )

        Assert.assertEquals(315.0, wpt.bearingTo(wpt2))
    }

    @Test
    fun bearingToSouthWest() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1.0,
                wpt.longitude-1.0
        )

        Assert.assertEquals(225.0, wpt.bearingTo(wpt2))
    }


    @Test
    fun bearingToSouthEast() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude-1,
                wpt.longitude+1
        )

        Assert.assertEquals(135.0, wpt.bearingTo(wpt2))
    }

    @Test
    fun bearingToNorthEast() {
        val wpt2 = Waypoint(
                1,
                "Oaze Deep",
                "Fl(2) G 5s",
                16.5F,
                wpt.latitude+1,
                wpt.longitude+1
        )

        Assert.assertEquals(45.0, wpt.bearingTo(wpt2))
    }
}
