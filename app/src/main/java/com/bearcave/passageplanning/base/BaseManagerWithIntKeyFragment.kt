package com.bearcave.passageplanning.base

import com.bearcave.passageplanning.base.database.DatabaseElement

/**
 *
 * @author Michał Wąsowicz
 * @since 17.08.17
 * @version 1.0
 */
abstract class BaseManagerWithIntKeyFragment<Dao : DatabaseElement>() : BaseManagerFragment<Dao, Int>() {

    override fun onDataCreated(result: Dao){
        adapter!!.add(
                read(
                        insert(result).toInt()
                )
        )
    }
}