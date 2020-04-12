package com.example.concordia_campus_guide.models;

public class BusStop extends Place {

    public static final Coordinates SGW_SHUTTLE_STOP = new Coordinates( 45.497025978950774, -73.57849985361099);
    public static final Coordinates LOYOLA_SHUTTLE_STOP = new Coordinates(45.4584776424937, -73.638241700828);

    public BusStop(String campus) {
        if (campus.equals("SGW")) {
            this.setCenterCoordinates(SGW_SHUTTLE_STOP);
        } else {
            this.setCenterCoordinates(LOYOLA_SHUTTLE_STOP);
        }
        this.setCampus(campus);
    }

    @Override
    public String getDisplayName() {
        return "Shuttle " + getCampus() + " stop";
    }


}
