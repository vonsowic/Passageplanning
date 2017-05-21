package com.bearcave.passageplanning.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;



public class Route implements Serializable{

    private String name;
    private ArrayList<Integer> waypointsId;

    public Route(String name, ArrayList<Integer> waypointsId) {
        this.name = name;
        this.waypointsId = waypointsId;
    }


    public String getName() {
        return name;
    }

    public ArrayList<Integer> getWaypointsIds() {
        return waypointsId;
    }


}
