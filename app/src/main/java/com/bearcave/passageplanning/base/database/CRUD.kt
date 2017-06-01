package com.bearcave.passageplanning.base.database


import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey

interface CRUD<T> : CRUDWithCustomKey<T, Int>
