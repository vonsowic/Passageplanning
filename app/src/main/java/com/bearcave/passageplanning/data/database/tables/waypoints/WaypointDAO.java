package com.bearcave.passageplanning.data.database.tables.waypoints;


import android.os.Parcel;
import android.os.Parcelable;

import com.bearcave.passageplanning.data.database.tables.base.DatabaseElement;
import com.bearcave.passageplanning.thames_tide_provider.web.configurationitems.Gauge;
import com.bearcave.passageplanning.utils.Waypoint;

import org.jetbrains.annotations.NotNull;

public class WaypointDAO
        extends com.bearcave.passageplanning.utils.Waypoint
        implements DatabaseElement,
        Parcelable  {

    private Integer id = -2;


    public WaypointDAO(Waypoint base){
        super(base.getName(), base.getNote(), base.getCharacteristic(), base.getUkc(), base.getLatitude(), base.getLongitude(), base.getGauge());
    }

    public WaypointDAO(Integer id, @NotNull String name, String note, @NotNull String characteristic, float ukc, double latitude, double longitude, @NotNull Gauge gauge) {
        super(name, note, characteristic, ukc, latitude, longitude, gauge);
        this.id = id;
    }

    public WaypointDAO(Integer id, @NotNull String name, String note, @NotNull String characteristic, float ukc, String latitude, String longitude, @NotNull Gauge gauge) {
        super(name, note, characteristic, ukc, latitude, longitude, gauge);
        this.id = id;
    }

    public WaypointDAO(@NotNull Parcel parcel) {
        this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readFloat(),
                parcel.readDouble(),
                parcel.readDouble(),
                Gauge.getById(parcel.readInt())
        );
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeInt(id);
        dest.writeString(getName());
        dest.writeString(getNote());
        dest.writeString(getCharacteristic());
        dest.writeFloat(getUkc());
        dest.writeDouble(getLatitude());
        dest.writeDouble(getLongitude());
        dest.writeInt(getGauge().getId());
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<WaypointDAO> CREATOR = new Creator<WaypointDAO>() {
        @Override
        public WaypointDAO createFromParcel(Parcel in) {
            return new WaypointDAO(in);
        }

        @Override
        public WaypointDAO[] newArray(int size) {
            return new WaypointDAO[size];
        }
    };

    @Override
    public Integer getId() {
        return id;
    }
}
