package com.bearcave.passageplanning

import android.app.Application
import android.content.Context

/**
 *
 * @author Michał Wąsowicz
 * @since 23.08.17
 * @version 1.0
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        contextHolder = applicationContext
    }

    companion object {
        private var contextHolder: Context? = null
        val context: Context
            get() = contextHolder ?: throw UninitializedPropertyAccessException()
    }
}