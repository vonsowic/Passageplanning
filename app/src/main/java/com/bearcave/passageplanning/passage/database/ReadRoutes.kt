package com.bearcave.passageplanning.passage.database

import com.bearcave.passageplanning.routes.database.route.RouteDAO

/**
 *
 * @author Michał Wąsowicz
 * @since 28.05.17
 * @version 1.0
 */
interface ReadRoutes {
    fun readAllRoutes(): List<RouteDAO>
}