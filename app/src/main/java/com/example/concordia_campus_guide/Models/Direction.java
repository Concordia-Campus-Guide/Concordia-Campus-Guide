package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class Direction {
    private LatLng start;
    private LatLng end;
    private Date startTime;
    private Date endTime;
    private TransportType transportType;
    private String description;

    public Direction(LatLng start, LatLng end, TransportType transportType, String description) {
        this.start = start;
        this.end = end;
        this.transportType = transportType;
        this.description = description;
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

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}