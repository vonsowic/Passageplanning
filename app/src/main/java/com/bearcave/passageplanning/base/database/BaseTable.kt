package com.bearcave.passageplanning.base.database


import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey
import com.bearcave.passageplanning.data.database.ManagerListener

abstract class BaseTable<Dao : DatabaseElement>(manager: ManagerListener)
    : BaseTableWithCustomKey<Dao, Int>(manager),
        ManagerListener,
        CRUD<Dao> {

    override val idKey: String
        get() = BaseTableWithCustomKey.KEY_ID
}