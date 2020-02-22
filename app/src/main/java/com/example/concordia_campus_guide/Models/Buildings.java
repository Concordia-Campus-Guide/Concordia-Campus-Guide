package com.example.concordia_campus_guide.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Buildings {
    private Properties properties;

    private String type;

    private Geometry geometry;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}