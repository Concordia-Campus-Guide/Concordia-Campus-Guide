package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.ClassConstants;
import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;

public class Direction implements Serializable {
    private transient LatLng start;
    private transient LatLng end;
    private String transportType;
    private String description;
    private double duration;

    public Direction() {
    }

    public Direction(LatLng start, LatLng end, String transportType, String description, double duration) {
        this.start = start;
        this.end = end;
        this.transportType = transportType;
        this.description = description;
        this.duration = duration;
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getEnd() {
        return end;
    }

    public void setEnd(LatLng end) {
        this.end = end;
    }

    public @ClassConstants.TransportType String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration(){
        return this.duration;
    }

    public void setDuration(double duration){
        this.duration = duration;
    }
}