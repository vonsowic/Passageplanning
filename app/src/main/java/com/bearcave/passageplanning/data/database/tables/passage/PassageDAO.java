package com.bearcave.passageplanning.data.database.tables.passage;

import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.utils.Passage;

import java.util.ArrayList;
import java.util.Arrays;



public class PassageDAO extends Passage implements DatabaseElement {

    @Override
    public long getId() {
        return -1;
    }

    public PassageDAO(String name, ArrayList<Integer> waypointsId) {
        super(name, waypointsId);
    }

    public static ArrayList<Integer> parse(String waypoints) {
        ArrayList<Integer> waypointsIds = new ArrayList<>();
        for (String item: Arrays.asList(waypoints.split("\\s*,\\s*"))){
            waypointsIds.add(Integer.valueOf(item));
        }
        return waypointsIds;
    }

    @Override
    public String toString() {
        StringBuilder joiner = new StringBuilder();
        for(Integer id: getWaypointsIds()){
            joiner.append(id);
            joiner.append(",");
        }
        return joiner.toString();
    }
}
