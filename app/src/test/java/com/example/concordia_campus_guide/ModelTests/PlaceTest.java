package com.example.concordia_campus_guide.ModelTests;

import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.Place;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlaceTest {
    Place defaultPlace;
    Place placeWithCenterCoordinates;
    Place placeWithCampus;
    Coordinates centerCoordinates = new Coordinates(45.4972685,-73.5789475);
    String campus = "SGW";

    @Before
    public void init(){
        defaultPlace = new Place() {
            @Override
            public String getDisplayName() {
                return "H";
            }
        };

        placeWithCenterCoordinates = new Place(centerCoordinates) {
            @Override
            public String getDisplayName() {
                return "H";
            }
        };

        placeWithCampus = new Place(centerCoordinates, campus) {
            @Override
            public String getDisplayName() {
                return "H";
            }
        };

    }

    @Test
    public void getCenterCoordinates() {
        assertEquals(centerCoordinates, placeWithCenterCoordinates.getCenterCoordinates());
        assertEquals(centerCoordinates.getLatitude(), placeWithCenterCoordinates.getLatitude(), 0.001);
        assertEquals(centerCoordinates.getLongitude(), placeWithCenterCoordinates.getLongitude(), 0.001);
    }

    @Test
    public void getCenterCoordinatesLatLng() {
        assertEquals(LatLng.class, placeWithCenterCoordinates.getCenterCoordinatesLatLng().getClass());
        assertEquals(centerCoordinates.getLatitude(), placeWithCenterCoordinates.getCenterCoordinatesLatLng().latitude, 0.001);
        assertEquals(centerCoordinates.getLongitude(), placeWithCenterCoordinates.getCenterCoordinatesLatLng().longitude, 0.001);
    }

    @Test
    public void getCenterCoordinatesDoubleArray() {
        assertEquals(Double[].class, placeWithCenterCoordinates.getCenterCoordinatesDoubleArray().getClass());
        assertEquals(centerCoordinates.getLatitude(), placeWithCenterCoordinates.getCenterCoordinatesDoubleArray()[1], 0.001);
        assertEquals(centerCoordinates.getLongitude(), placeWithCenterCoordinates.getCenterCoordinatesDoubleArray()[0], 0.001);

    }

    @Test
    public void setCenterCoordinates() {
        Coordinates updateCenterCoordinates = new Coordinates(45.493622,-73.577003 );
        assertEquals(centerCoordinates, placeWithCenterCoordinates.getCenterCoordinates());
        assertEquals(centerCoordinates.getLatitude(), placeWithCenterCoordinates.getLatitude(), 0.001);
        assertEquals(centerCoordinates.getLongitude(), placeWithCenterCoordinates.getLongitude(), 0.001);

        placeWithCenterCoordinates.setCenterCoordinates(updateCenterCoordinates);
        assertEquals(updateCenterCoordinates, placeWithCenterCoordinates.getCenterCoordinates());
        assertEquals(updateCenterCoordinates.getLatitude(), placeWithCenterCoordinates.getLatitude(),0.001);
        assertEquals(updateCenterCoordinates.getLongitude(), placeWithCenterCoordinates.getLongitude(), 0.001);
    }

    @Test
    public void getId() {
        assertEquals(0,placeWithCampus.getId());
    }

    @Test
    public void setId() {
        long updateId = 1234567;
        assertEquals(0, placeWithCenterCoordinates.getId());
        placeWithCenterCoordinates.setId(updateId);
        assertEquals(1234567, placeWithCenterCoordinates.getId());
    }

    @Test
    public void getDisplayName() {
        assertEquals("H", defaultPlace.getDisplayName());
        assertEquals("H", placeWithCenterCoordinates.getDisplayName());
        assertEquals("H", placeWithCampus.getDisplayName());
    }

    @Test
    public void getCampus() {
        assertEquals("SGW", placeWithCampus.getCampus());
    }

    @Test
    public void setCampus() {
        String updateCampus = "LOY";
        assertEquals("SGW", placeWithCampus.getCampus());
        placeWithCampus.setCampus(updateCampus);
        assertEquals("LOY", placeWithCampus.getCampus());
    }
}