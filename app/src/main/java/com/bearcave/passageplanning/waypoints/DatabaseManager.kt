package com.bearcave.passageplanning.waypoints

import android.location.Location
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


/**
 * Created by miwas on 11.03.17.
 */
class DatabaseManager {
    private val WAYPOINTS = "waypoints"
    private val WAYPOINT = "waypoint"
    private val ROUTES = "routes"

    private val ID = "id"
    private val NAME = "name"
    private val CHARACTERISTIC = "characteristic"
    private val POSITION = "position"
    private val UKC = "ukc"


    fun addWaypoint(waypoint: Waypoint){

    }

    fun addWaypoint(id: Int, name: String, characteristic: String, ukc: Float, longitude: Double, latitude: Double){
        addWaypoint(
                Waypoint(
                        id,
                        name,
                        characteristic,
                        ukc,
                        latitude,
                        longitude
                )
        )
    }

    fun getWaypointById(id: Int){}

    fun createDatabase(): org.w3c.dom.Document? {
        val builderFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = builderFactory.newDocumentBuilder()
        val database = docBuilder.newDocument()

        val rootElement = database.createElement(WAYPOINTS)
        database.appendChild(rootElement)

        return database
    }

    fun loadDatabase(): Document {
        val doc = Jsoup.parse(
                File(WAYPOINTS),
                "UTF-8"
        )

        return doc
    }

    fun saveDatabase(){

    }

}