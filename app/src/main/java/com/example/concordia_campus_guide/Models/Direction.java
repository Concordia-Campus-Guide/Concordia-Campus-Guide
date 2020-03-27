package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class Direction {
    private LatLng start;
    private LatLng end;
    private Date startTime;
    private Date endTime;
    private TransportType type;
    private String description;

    public Direction(LatLng start, LatLng end, TransportType type, String description) {
        this.start = start;
        this.end = end;
        this.type = type;
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

    public TransportType getType() {
        return type;
    }

    public void setType(TransportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}