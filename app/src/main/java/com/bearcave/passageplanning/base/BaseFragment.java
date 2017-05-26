package com.bearcave.passageplanning.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The  most base fragment class for this app. All created fragments should extend this fragment.
 * @author Michał Wąsowicz
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Enables finding views by using ButterKnife.
     */
    private Unbinder unbinder;

    protected abstract int getTitle();

    protected abstract int layoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(getString(getTitle()));
        View view = inflater.inflate(layoutId(), container, false);
        findViews(view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * Fragments extending this class should connect to views from layouts in this section.
     * @param parent - main view
     */
    protected void findViews(View parent) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public BaseFragment() {
        // Required empty public constructor
    }

}
