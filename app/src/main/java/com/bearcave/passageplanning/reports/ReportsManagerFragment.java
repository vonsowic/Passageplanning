package com.bearcave.passageplanning.reports;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcave.passageplanning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsManagerFragment extends Fragment {


    public ReportsManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports_manager, container, false);
    }

}
