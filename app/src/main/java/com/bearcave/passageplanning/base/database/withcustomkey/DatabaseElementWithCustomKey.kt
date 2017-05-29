package com.bearcave.passageplanning.base.database.withcustomkey


interface DatabaseElementWithCustomKey<out Id> {
    val id: Id
    val name: String
}
