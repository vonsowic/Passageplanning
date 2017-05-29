package com.bearcave.passageplanning.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.bearcave.passageplanning.R;

/**
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 29.05.17
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

}
