package com.bearcave.passageplanning.data.database

import com.bearcave.passageplanning.base.database.withcustomkey.BaseTableWithCustomKey

interface OnDatabaseRequestedListener {
    fun onGetTableListener(tableId: Int): BaseTableWithCustomKey<*, *>
}