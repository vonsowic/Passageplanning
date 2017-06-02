package com.bearcave.passageplanning.routes.database

import com.bearcave.passageplanning.base.database.CRUD


interface RouteCRUD : CRUD<Route> {
    companion object {
        val ID = 101
    }
}
