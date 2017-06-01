package com.bearcave.passageplanning.passage.database

import com.bearcave.passageplanning.base.database.CRUD

/**
 * @author Michał Wąsowicz
 * *
 * @version 1.0
 * *
 * @since 27.05.17
 */

interface PassageCRUD : CRUD<Passage> {
    companion object {
        val ID = 103
    }
}
