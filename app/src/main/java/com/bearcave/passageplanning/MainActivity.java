package com.bearcave.passageplanning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * @author Michał Wąsowicz
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.fragment_placeholder, new PassageListViewFragment());
        //ft.commit();

    }
}
