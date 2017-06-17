package com.bearcave.passageplanning.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

import com.bearcave.passageplanning.R

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 29.05.17
 */

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

    }

    companion object {

    }
}
