package com.example.concordia_campus_guide.Models;
import java.util.List;
import java.util.Properties;

public class Features {
    public List<Buildings> getBuildings() {
        return features;
    }

    public void setBuildings(List<Buildings> buildings) {
        this.features = buildings;
    }

    public Features(List<Buildings> buildings) {
        this.features = buildings;
    }

    private List<Buildings> features;
}