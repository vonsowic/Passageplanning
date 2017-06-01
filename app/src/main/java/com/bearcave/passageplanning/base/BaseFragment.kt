package com.bearcave.passageplanning.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * The  most base fragment class for this app. All created fragments should extend this fragment.
 * @author Michał Wąsowicz
 */
abstract class BaseFragment : Fragment() {

    /**
     * Enables finding views by using ButterKnife.
     */
    private var unbinder: Unbinder? = null

    protected abstract val title: Int

    protected abstract fun layoutId(): Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        activity.title = getString(title)
        val view = inflater!!.inflate(layoutId(), container, false)
        findViews(view)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    /**
     * Fragments extending this class should connect to views from layouts in this section.
     * @param parent - main view
     */
    protected open fun findViews(parent: View) {}


    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

}// Required empty public constructor
