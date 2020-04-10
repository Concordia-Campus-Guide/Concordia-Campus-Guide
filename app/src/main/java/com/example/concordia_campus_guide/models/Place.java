package com.example.concordia_campus_guide.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity(tableName = "places",
        indices = {@Index("id")}
        )
public abstract class Place {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @Embedded
    protected Coordinates centerCoordinates;

    @ColumnInfo(name ="campus")
    protected String Campus;

    public Place(Coordinates centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public Place(Coordinates centerCoordinates, String campus) {
        this.centerCoordinates = centerCoordinates;
        this.Campus = campus;
    }

    public Place() {}

    public Coordinates getCenterCoordinates() {
        return centerCoordinates;
    }

    public double getLatitude(){
        return centerCoordinates.getLatitude();
    }

    public double getLongitude(){
        return centerCoordinates.getLongitude();
    }

    public LatLng getCenterCoordinatesLatLng() {
        return new LatLng(centerCoordinates.latitude, centerCoordinates.longitude);
    }

    public Double[] getCenterCoordinatesDoubleArray(){
        return new Double[]{centerCoordinates.longitude, centerCoordinates.latitude};
    }

    public void setCenterCoordinates(Coordinates centerCoordinates) {
        this.centerCoordinates = centerCoordinates;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public abstract String getDisplayName();

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        this.Campus = campus;
    }
}
