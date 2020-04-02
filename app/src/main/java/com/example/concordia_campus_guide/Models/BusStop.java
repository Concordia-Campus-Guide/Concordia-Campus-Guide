package com.example.concordia_campus_guide.Models;

import com.example.concordia_campus_guide.ClassConstants;

public class BusStop extends Place {

    public BusStop(String campus) {
        if (campus.equals("SGW")) {
            this.setCenterCoordinates(ClassConstants.SGW_SHUTTLE_STOP);
        } else {
            this.setCenterCoordinates(ClassConstants.LOYOLA_SHUTTLE_STOP);
        }
        this.setCampus(campus);
    }

    @Override
    public String getDisplayName() {
        return "Shuttle " + getCampus() + " stop";
    }


}
