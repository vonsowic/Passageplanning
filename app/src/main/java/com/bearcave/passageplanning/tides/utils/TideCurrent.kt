package com.bearcave.passageplanning.tides.utils


import com.bearcave.passageplanning.tides.database.TideCurrentInfoHandler
import com.bearcave.passageplanning.tides.utils.exceptions.TideStationNotFound

enum class TideCurrent(val id: Int, val gaugeId: Int, val tideCurrentStation: String, val spring: FloatArray, val neap: FloatArray) {


    GRAVESEND_REACH(
            0,
            6,
            "Gravesend Reach",
            floatArrayOf(
                    0.7f, 2.3f, 2.4f, 1.9f, 1.7f, 1.1f, 0.8f, 2.7f, 2.7f, 2.4f, 1.8f, 0.7f, 0.1f
                    ),
            floatArrayOf(
                    0.4f, 1.6f, 1.6f, 1.3f, 1.1f, 0.7f, 0.6f, 1.9f, 1.9f, 1.7f, 1.3f, 0.5f, 0.1f
                    )
    ),


    SEA_REACH(
            1,
            2,
            "Sea Reach",
            floatArrayOf(
                    0f, 0.2f, 1.6f, 1.8f, 1.6f, 1.2f, 0.8f, 0.6f, 1.9f, 2.5f, 1.8f, 0.8f, 0.2f
                    ),
            floatArrayOf(
                    0f, 0.2f, 1.1f, 1.2f, 1.1f, 0.8f, 0.6f, 0.4f, 1.3f, 1.7f, 1.2f, 0.6f, 0.2f
            )
    ),


    WARPS(
            2,
            2,
            "Warps",
            floatArrayOf(
                    0.4f, 0.8f, 1.7f, 1.7f, 1.4f, 1.2f, 0.3f, 1.2f, 2.3f, 2.4f, 1.8f, 1.2f, 0.8f
                    ),
            floatArrayOf(
                    0.2f, 0.5f, 1.1f, 1.1f, 0.8f, 0.7f, 0.1f, 0.7f, 1.4f, 1.5f, 1.1f, 0.7f, 0.5f
            )
    ),


    N_RED_SAND(
            3,
            2,
            "N Red Sand",
            floatArrayOf(
                    0.2f, 1f, 1.7f, 1.7f, 1.6f, 1.3f, 0.5f, 0.6f, 2.1f, 2.4f, 1.9f, 1f, 0.2f
                    ),
            floatArrayOf(
                    0.1f, 0.7f, 1.1f, 1.1f, 1.1f, 0.9f, 0.3f, 0.4f, 1.4f, 1.6f, 1.2f, 0.7f, 0.2f
            )
    ),


    PRINCES_IN(
            4,
            2,
            "Princes In",
            floatArrayOf(
                    0.3f, 1.2f, 1.5f, 1.5f, 1.6f, 0.9f, 0.5f, 1.7f, 2.2f, 2f, 1.3f, 0.8f, 0.4f
            ),
            floatArrayOf(
                    0.1f, 0.5f, 0.9f, 0.9f, 1f, 0.6f, 0.3f, 1f, 1.4f, 1.2f, 1f, 0.6f, 0.4f
            )
    ),

    PRINCES_NO3(
            5,
            2,
            "Princes no3",
            floatArrayOf(
                    0.5f, 0.6f, 1.8f, 2f, 2.5f, 1.9f, 0.6f, 1.5f, 2.5f, 2.4f, 2.1f, 1.7f, 0.8f
            ),
            floatArrayOf(
                0.2f, 0.2f, 0.9f, 1.4f, 1.6f, 1.3f, 0.4f, 0.8f, 1.4f, 1.5f, 1.2f, 0.9f, 0.5f
            )
    ),

    PRINCES_OUT(
            6,
            2,
            "Princes Out",
            floatArrayOf(
                    0.2f, 1.1f, 1.3f, 1.5f, 1.6f, 1.3f, 0.5f, 0.6f, 1.7f, 2.1f, 1.6f, 1.1f, 0.2f
                    ),
            floatArrayOf(
                    0.1f, 0.7f, 0.8f, 1.1f, 1f, 0.8f, 0.3f, 0.4f, 1.1f, 1.4f, 1f, 0.7f, 0.1f
            )
    ),

    NE_SPIT(
            7,
            2,
            "NE Spit",
            floatArrayOf(
                    1.2f, 1.3f, 1.3f, 1.2f, 1.7f, 1.9f, 1.6f, 1.3f, 1.4f, 1.5f, 1.3f, 1.1f, 1.2f
                    ),
            floatArrayOf(
                    0.7f, 0.8f, 0.7f, 0.7f, 1f, 1.1f, 0.9f, 0.8f, 0.8f, 0.8f, 0.7f, 0.6f, 0.7f
            )
    );

    fun getValue(id: Int, type: TideType?) =
            when(type){
                TideType.SPRING -> getSpringValue(id)
                TideType.NEAP   -> getNeapValue(id)
                null            -> (getSpringValue(id) + getNeapValue(id)) / 2
            }

    fun getValue(id: Int, lowWater: Float, highWater: Float) = getValue(id, getType(lowWater, highWater))

    fun getValue(tideCurrentInfo: TideCurrentInfoHandler) = getValue(
            tideCurrentInfo.hoursToHighWater,
            tideCurrentInfo.lowWater,
            tideCurrentInfo.highWater
    )

    fun getSpringValue(id: Int) = spring[id+6]

    fun getNeapValue(id: Int) = neap[id+6]

    fun names() = values().map { it.tideCurrentStation }

    companion object {
        fun getById(id: Int): TideCurrent {
            values()
                    .filter { it.id == id }
                    .forEach { return it }

            throw TideStationNotFound()
        }

        fun getByName(name: String): TideCurrent {
            values()
                    .filter { it.tideCurrentStation == name }
                    .forEach { return it }

            throw TideStationNotFound()
        }

        fun getType(lowWater: Float, highWater: Float): TideType? {
            val height = highWater-lowWater
            return when {
                height < 4f -> TideType.NEAP
                height > 5f -> TideType.SPRING
                else -> null
            }
        }
    }


}