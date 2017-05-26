package com.bearcave.passageplanning.base;


import android.view.View;
import android.widget.ExpandableListView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.base.CRUD;
import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.DatabaseElementWithCustomKey;

import butterknife.ButterKnife;

/**
 * Shows ExpandableListView using adapter, which extends BaseManagerAdapter.
 * @see BaseManagerAdapter
 * @version 1.0
 * @author Michał Wąsowicz
 */
public abstract class BasePoorManagerFragment<DAO extends DatabaseElementWithCustomKey<T>, T> extends BaseFragment
        implements CRUD<DAO> {

    BaseManagerAdapter<DAO, T> adapter;

    protected BaseManagerAdapter<DAO, T> getAdapter(){
        return adapter;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_base_poor_manager;
    }

    protected abstract BaseManagerAdapter createAdapter();

    @Override
    protected void findViews(View view) {
        super.findViews(view);
        ExpandableListView listView = ButterKnife.findById(view, R.id.list_view);
        adapter = createAdapter();
        listView.setAdapter(adapter);
    }
}
