package com.bearcave.passageplanning.data.database.tables.route;

import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.utils.Route;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by miwas on 19.05.17.
 */

public class RouteDAO extends Route implements DatabaseElement {

    private long id = -1;

    @Override
    public long getId() {
        return id;
    }

    public RouteDAO(long id, String name, ArrayList<Integer> waypointsId) {
        super(name, waypointsId);
        this.id = id;
    }

    public RouteDAO(long id) {
        this.id = id;
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
        for(Integer id: getWaypointsId()){
            joiner.append(id);
            joiner.append(",");
        }
        return joiner.toString();
    }
}
