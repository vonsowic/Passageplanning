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
        private set
    var name: String = ""
        private set
    var characteristic: String = ""
        private set
    var ukc: Float = 0F
        private set
    var longitude: Double = 0.0
        private set
    var latitude: Double = 0.0
        private set


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
        val longDiff = waypoint.longitude - this.longitude
        val y = Math.sin(longDiff) * Math.cos(waypoint.latitude)
        val x = Math.cos(this.latitude) * Math.sin(waypoint.latitude) - Math.sin(this.latitude) * Math.cos(waypoint.latitude) * Math.cos(longDiff)

        return (Math.toDegrees(Math.atan2(y, x)) + 360 ) % 360
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