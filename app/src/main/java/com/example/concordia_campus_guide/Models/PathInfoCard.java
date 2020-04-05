package com.example.concordia_campus_guide.Models;
import java.io.Serializable;

public class PathInfoCard implements Serializable {
    private String type;
    private double duration;
    private String description;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PathInfoCard(String type, double duration, String description) {
        this.type = type;
        this.duration = duration;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
