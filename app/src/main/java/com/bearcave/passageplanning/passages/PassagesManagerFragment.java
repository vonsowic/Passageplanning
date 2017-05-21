package com.bearcave.passageplanning.passages;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.base.BaseManagerAdapter;
import com.bearcave.passageplanning.base.BaseManagerFragment;
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener;
import com.bearcave.passageplanning.data.database.tables.passage.PassageCRUD;
import com.bearcave.passageplanning.data.database.tables.passage.PassageDAO;
import com.bearcave.passageplanning.data.database.tables.passage.PassageTable;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassagesManagerFragment extends BaseManagerFragment
        implements PassageCRUD {

    private PassageTable database;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        OnDatabaseRequestedListener databaseHolder = (OnDatabaseRequestedListener) context;
        database = (PassageTable) databaseHolder.onGetTableListener(PassageCRUD.ID);
    }

    @Override
    public Integer insert(PassageDAO element) {
        return null;
    }

    @Override
    public PassageDAO read(Integer id) {
        return null;
    }

    @Override
    public List<PassageDAO> readAll() {
        return database.readAll();
    }


    @Override
    public int update(PassageDAO element) {
        return 0;
    }

    @Override
    public int delete(PassageDAO element) {
        return 0;
    }


    @Override
    protected BaseManagerAdapter getAdapter() {
        return new PassageManagerAdapter(this, getContext());
    }

    @Override
    protected int getTitle() {
        return R.string.passages_menu;
    }

    public PassagesManagerFragment() {
        // Required empty public constructor
    }

}
