package com.bearcave.passageplanning.tides.web.configurationitems


import com.bearcave.passageplanning.tides.web.configurationitems.exceptions.GaugeNotFoundException

enum class Gauge constructor(val id: Int, val humanCode: String, val code: String) {
    MARGATE(            1,      "Margate",               "0103"      ),
    SOUTHEND(           2,      "Southend",              "0110"      ),
    CORYTON(            3,      "Coryton",               "0110A"     ),
    TILBURY(            4,      "Tilbury",               "0111"      ),
    NORTH_WOOLWICH(     5,      "North Woolwich",        "0112"      ),
    LONDON_BRIDGE(      6,      "London Bridge",         "0113"      ),
    CHELSEA_BRIDGE(     7,      "Chealse Bridge",        "0113A"     ),
    RICHMOND(           8,      "Richmond",              "0116"      ),
    SHIVERING_SAND(     9,      "Shivering Sand",        "0116A"     ),
    WALTON_ON_THE_NAZE( 10,     "Walton on the Naze",    "0129"      );


    companion object {

        fun getById(id: Int): Gauge {
            for (item in Gauge.values()) {
                if (item.id == id) {
                    return item
                }
            }

            throw GaugeNotFoundException()
        }

        fun getByCode(code: String): Gauge {
            for (item in Gauge.values()) {
                if (item.code == code) {
                    return item
                }
            }

            throw GaugeNotFoundException()
        }

        fun getByName(name: String): Gauge {
            for (item in Gauge.values()) {
                if (item.humanCode == name) {
                    return item
                }
            }

            throw GaugeNotFoundException()
        }
    }
}
