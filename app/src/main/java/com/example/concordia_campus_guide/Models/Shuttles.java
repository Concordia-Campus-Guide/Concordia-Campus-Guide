package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;

public class Shuttles {

    private List<Shuttle> shuttles;

    public Shuttles(){ shuttles = new ArrayList<Shuttle>(); }

    public Shuttles(List<Shuttle> shuttles) {
        shuttles = shuttles;
    }

    public List<Shuttle> getShuttles() {
        return shuttles;
    }

    public void setShuttles(List<Shuttle> shuttles) {
        this.shuttles = shuttles;
    }
}
