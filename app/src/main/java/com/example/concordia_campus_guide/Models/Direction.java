package com.example.concordia_campus_guide.Models;

import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class Direction {
    private LatLng start;
    private LatLng end;
    private Date startTime;
    private Date endTime;
    private TransitType type;
    private String description;

    public Direction(LatLng start, LatLng end, TransitType type, String description) {
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

    public TransitType getType() {
        return type;
    }

    public void setType(TransitType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}