package com.bearcave.passageplanning.data.database.tables.route;


import android.os.Parcel;
import android.os.Parcelable;

import com.bearcave.passageplanning.base.OnNameRequestedListener;
import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.utils.Route;

import java.util.ArrayList;
import java.util.Arrays;



public class RouteDAO extends Route implements DatabaseElement, OnNameRequestedListener, Parcelable {

    long id = -2;

    @Override
    public long getId() {
        return id;
    }

    public RouteDAO(long id, String name, ArrayList<Integer> waypointsId) {
        super(name, waypointsId);
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
        for(Integer id: getWaypointsIds()){
            joiner.append(id);
            joiner.append(",");
        }
        return joiner.toString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(getName());
        dest.writeList(getWaypointsIds());
    }

    protected RouteDAO(Parcel in) {
        this(in.readLong(), in.readString(), in.readArrayList(Route.class.getClassLoader()));
    }


    public static final Creator<RouteDAO> CREATOR = new Creator<RouteDAO>() {
        @Override
        public RouteDAO createFromParcel(Parcel in) {
            return new RouteDAO(in);
        }

        @Override
        public RouteDAO[] newArray(int size) {
            return new RouteDAO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
