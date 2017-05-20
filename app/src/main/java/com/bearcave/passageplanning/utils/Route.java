package com.bearcave.passageplanning.utils;

import java.util.ArrayList;



public class Route {

    private String name;
    private ArrayList<Integer> waypointsId;

    public Route(String name, ArrayList<Integer> waypointsId) {
        this.name = name;
        this.waypointsId = waypointsId;
    }

    public Route(){
        this.name = "";
        this.waypointsId = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getWaypointsId() {
        return waypointsId;
    }

    public void setWaypointsId(ArrayList<Integer> waypointsId) {
        this.waypointsId = waypointsId;
    }
}
