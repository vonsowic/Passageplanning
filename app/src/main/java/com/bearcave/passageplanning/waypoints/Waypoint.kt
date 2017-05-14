package com.bearcave.passageplanning.waypoints

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.Serializable

/**
 * Data structure representing single waypoint.
 */
class Waypoint: Serializable {

    var id: Int = 1
    var name: String = ""
    var characteristic: String = ""
    var ukc: Float = 0F
    var longitude: Double = 0.0
    var latitude: Double = 0.0


    constructor(id: Int,
                name: String,
                characteristic: String,
                ukc: Float,
                latitude: Double,
                longitude: Double){

        this.id = id
        this.name = name
        this.characteristic = characteristic
        this.ukc = ukc
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(element: Element): this(
            element.attr("id").toInt(),
            element.attr("name"),
            element.attr("characteristic"),
            element.attr("ukc").toFloat(),
            element.attr("latitude").toDouble(),
            element.attr("longitude").toDouble()
    )

    constructor(xmlString: String): this(Jsoup.parse(xmlString).body().getElementsByTag("waypoint").first())

    constructor()

    fun toXmlElement(): Element {
        val element = Element("waypoint")

        // convert every waypoint variable to xml tag
        for (field in this.javaClass.declaredFields) {
            element.attr(
                    field.name.toLowerCase(),       // attribute key
                    field.get(this).toString())     // attribute value
        }

        return element
    }

    fun toXml(): String = toXmlElement().toString()

    /**
     * @return the approximate initial bearing in degrees East of true North when traveling along the shortest path between this location and the given location.
     */
    fun bearingTo(waypoint: Waypoint): Double? {
        val milion = 1000000.0
        val lat1Rad = Math.toRadians(this.latitude/milion)
        val lat2Rad = Math.toRadians(waypoint.latitude/milion)
        val deltaLonRad = Math.toRadians(waypoint.longitude/milion - this.longitude/milion)

        val y = Math.sin(deltaLonRad) * Math.cos(lat2Rad)
        val x = Math.cos(lat1Rad) * Math.sin(lat2Rad) - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLonRad)
        return Math.toDegrees(Math.atan2(y, x) + 360.0) % 360.0

    }

    /**
     * @return the approximate distance in meters between this location and the given location.
     */
    fun distanceTo(waypoint: Waypoint): Double {
        return Math.sqrt(
                Math.pow(this.longitude-waypoint.longitude, 2.0) + Math.pow(this.latitude-waypoint.latitude, 2.0)
        )
    }

    private fun ukc(ukc: String){
        this.ukc = ukc.toFloat()
    }

    private fun id(id: String){
        this.id = id.toInt()
    }

    private fun longitude(value: String) {
        this.longitude = value.toDouble()
    }

    private fun latitude(value: String) {
        this.latitude = value.toDouble()
    }

    override fun equals(other: Any?): Boolean {
        val wpt: Waypoint = other as Waypoint
        return id.equals(wpt.id)
                && (name == wpt.name)
                && characteristic.equals(wpt.characteristic)
                && longitude.equals(wpt.longitude)
                && latitude.equals(wpt.latitude)
    }
}