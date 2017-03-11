package com.bearcave.passageplanning;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bearcave.passageplanning.list_of_waypoints.PassageListViewFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
