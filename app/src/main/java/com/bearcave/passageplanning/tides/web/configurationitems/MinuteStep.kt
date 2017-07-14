package com.bearcave.passageplanning.tides.web.configurationitems

import java.util.*


enum class MinuteStep constructor(val value: Int) {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    HALF_HOUR(30),
    HOUR(60);

    companion object {
        fun getByValue(value: Int): MinuteStep {
            values()
                    .filter { it.value == value }
                    .forEach { return it }

            throw NoSuchElementException()
        }
    }
}
