package com.bearcave.passageplanning.tides.web.configurationitems


enum class NumberOfDays private constructor(val length: Int) {
    ONE(1),
    WEEK(7),
    MONTH(30)
}
