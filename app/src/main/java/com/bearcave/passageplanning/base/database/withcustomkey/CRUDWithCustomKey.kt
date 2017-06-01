package com.bearcave.passageplanning.base.database.withcustomkey

interface CRUDWithCustomKey<T, Key> {
    /**
     * @param element
     * *
     * @return the row ID of the newly inserted row
     */
    fun insert(element: T): Long

    fun read(id: Key): T
    fun readAll(): List<T>


    /**
     * @param element
     * *
     * @return the number of rows affected
     */
    fun update(element: T): Int


    /**
     * @param element
     * *
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise.
     */
    fun delete(element: T): Int
}
