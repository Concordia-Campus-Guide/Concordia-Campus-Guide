package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.Database.Converters.DoubleListConverter;
import com.example.concordia_campus_guide.Database.Converters.StringListConverter;
import com.google.android.gms.maps.model.LatLng;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "places")
public class Place {



    @ColumnInfo(name ="place_id")
    private long id;

    @TypeConverters(DoubleListConverter.class)
    @ColumnInfo(name ="center_coordinates")
    protected Double[] centerCoordinates;

    public Place(Double[] centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public Place() {

    }

    public Double[] getCenterCoordinates() {
        return centerCoordinates;
    }

    public LatLng getCenterCoordinatesLatLng() {
        return new LatLng(centerCoordinates[0], centerCoordinates[1]);
    }

    public void setCenterCoordinates(Double[] centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
