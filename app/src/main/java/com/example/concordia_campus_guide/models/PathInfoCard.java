package com.example.concordia_campus_guide.models;
import java.io.Serializable;

public class PathInfoCard implements Serializable {
    private String type;
    private double duration;
    private String description;

    public PathInfoCard(String type, double duration, String description) {
        this.type = type;
        this.duration = duration;
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
