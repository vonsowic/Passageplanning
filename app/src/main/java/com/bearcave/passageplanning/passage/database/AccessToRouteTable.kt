package com.bearcave.passageplanning.passage.database

import com.bearcave.passageplanning.data.database.ManagerListener
import com.bearcave.passageplanning.routes.database.Route

/**
 *
 * @author Michał Wąsowicz
 * @since 28.05.17
 * @version 1.0
 */
interface AccessToRouteTable : ManagerListener{
    fun readRoute(id: Int): Route
}