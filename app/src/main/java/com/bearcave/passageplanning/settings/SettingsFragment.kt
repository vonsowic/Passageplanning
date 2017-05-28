package com.bearcave.passageplanning.settings

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseFragment

/**
 *
 * @author Michał Wąsowicz
 * @since 28.05.17
 * @version 1.0
 */
class SettingsFragment : BaseFragment(){
    override fun getTitle(): Int {
        return R.id.settings
    }

    override fun layoutId(): Int {
        return R.layout.fragment_settings
    }
}