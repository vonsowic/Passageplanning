package com.bearcave.passageplanning.tasks


/**
 *
 * @author Michał Wąsowicz
 * @since 14.07.17
 * @version 1.0
 */
interface TaskUpdaterListener {
    fun onNoInternetConnection()
    fun onTaskUpdated(progress: Int)
    fun onTaskFinished()
}