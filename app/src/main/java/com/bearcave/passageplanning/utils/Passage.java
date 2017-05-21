package com.bearcave.passageplanning.utils;

import java.io.Serializable;
import java.util.ArrayList;



public class Passage implements Serializable{

    private String name;
    private ArrayList<Integer> waypointsId;

    public Passage(String name, ArrayList<Integer> waypointsId) {
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
