package com.bearcave.passageplanning.tides.web.configurationitems


enum class MinuteStep private constructor(val step: Int) {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    HALF_HOUR(30),
    HOUR(60)
}
