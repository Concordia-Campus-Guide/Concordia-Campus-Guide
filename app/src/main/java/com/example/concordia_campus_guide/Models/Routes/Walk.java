package com.example.concordia_campus_guide.Models.Routes;

import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsStep;
import com.example.concordia_campus_guide.R;

public class Walk extends TransportType {
    private String duration;
    private int icon = R.drawable.ic_directions_walk_red;

    public Walk() {}

    public Walk(DirectionsStep step) {
        duration = step.duration.text;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
