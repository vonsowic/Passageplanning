package com.bearcave.passageplanning.passages.database

import com.bearcave.passageplanning.routes.database.Route

/**
 *
 * @author Michał Wąsowicz
 * @since 28.05.17
 * @version 1.0
 */
interface ReadRoutes {
    fun readAllRoutes(): List<Route>
}