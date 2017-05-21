package com.bearcave.passageplanning.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.base.CRUD;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * BaseManager fragment without FloatingActionButton.
 */
public abstract class BasePoorManagerFragment<DAO extends OnNameRequestedListener> extends BaseFragment implements CRUD<DAO> {

    BaseManagerAdapter<DAO> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ExpandableListView listView = ButterKnife.findById(view, R.id.list_view);
        adapter = createAdapter();
        listView.setAdapter(adapter);

        return view;
    }

    protected BaseManagerAdapter<DAO> getAdapter(){
        return adapter;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_base_poor_manager;
    }

    protected abstract BaseManagerAdapter createAdapter();
}
