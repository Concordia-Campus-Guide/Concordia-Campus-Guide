package com.example.concordia_campus_guide.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.concordia_campus_guide.Database.Converters.DoubleListConverter;
import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "places")
public abstract class Place {

    @Ignore
    private long id;

    @TypeConverters(DoubleListConverter.class)
    @ColumnInfo(name ="center_coordinates")
    protected Double[] centerCoordinates;

    public Place(Double[] centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public Place() {}

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
    
    public abstract String getDisplayName();
}
