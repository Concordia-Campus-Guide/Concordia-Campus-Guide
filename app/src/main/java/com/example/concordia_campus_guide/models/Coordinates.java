package com.example.concordia_campus_guide.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Coordinates implements Serializable {

    double latitude;
    double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return latitude == that.latitude  &&
                longitude == that.longitude;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public List<Double> toListDoubleLongLat(){
        return new ArrayList<>(Arrays.asList(longitude, latitude));
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method returns the euclidean distance between this and another point based on the pythagorean theorem.
     * @param other
     * @return
     */
    public double getEuclideanDistanceFrom(Coordinates other){
        final double resultLatDiff = Math.abs(this.latitude- other.latitude);
        final double resultLongDiff = Math.abs(this.longitude - other.longitude);
        return Math.sqrt(Math.pow(resultLatDiff, 2) + Math.pow(resultLongDiff, 2));
    }

    public LatLng getLatLng() {
        return new LatLng(this.longitude, this.latitude);
    }
}
