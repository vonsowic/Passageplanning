package com.bearcave.passageplanning


import com.bearcave.passageplanning.base.BaseFragment


class AboutFragment : BaseFragment() {

    override val title: String
        get() = context.getString(R.string.about)

    override fun layoutId() = R.layout.fragment_about
}
