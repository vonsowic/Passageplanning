package com.bearcave.passageplanning.passages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bearcave.passageplanning.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassagesManagerFragment extends Fragment {


    public PassagesManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passages_manager, container, false);
    }

}
