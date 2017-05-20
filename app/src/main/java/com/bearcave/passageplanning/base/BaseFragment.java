package com.bearcave.passageplanning.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bearcave.passageplanning.R;
import com.bearcave.passageplanning.data.database.tables.base.CRUD;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements CRUD {

    public BaseFragment() {
        // Required empty public constructor
    }
}
