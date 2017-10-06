package com.bearcave.passageplanning.tides.utils


import com.bearcave.passageplanning.tides.utils.exceptions.GaugeNotFoundException

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
            Gauge.values()
                    .filter { it.id == id }
                    .forEach { return it }

            throw GaugeNotFoundException("Id $id does not exist.")
        }

        fun getByCode(code: String): Gauge {
            Gauge.values()
                    .filter { it.code == code }
                    .forEach { return it }

            throw GaugeNotFoundException("Gauge with code $code does not exist.")
        }

        fun getByName(name: String): Gauge {
            Gauge.values()
                    .filter { it.humanCode == name }
                    .forEach { return it }

            throw GaugeNotFoundException("Gauge $name does not exist.")
        }
    }
}
