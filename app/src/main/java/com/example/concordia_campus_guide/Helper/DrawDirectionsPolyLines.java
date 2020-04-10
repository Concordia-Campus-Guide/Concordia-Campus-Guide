package com.example.concordia_campus_guide.helper;

import android.graphics.Color;

import com.example.concordia_campus_guide.adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class DrawDirectionsPolyLines {

    public void drawOutdoorPath(List<DirectionWrapper> outdoorDirections, GoogleMap map) {
        for (DirectionWrapper directionWrapper : outdoorDirections) {
            int color = 0;
            if (directionWrapper.getTransitDetails() != null) {
                color = Color.parseColor(directionWrapper.getTransitDetails().line.color);
            }
            PolylineOptions polylineOptions = stylePolyLine(directionWrapper.getDirection().getTransportType(), color);
            List<com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.LatLng> polylines = directionWrapper.getPolyline().decodePath();

            for (com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.LatLng polyine : polylines) {
                polylineOptions.add(new LatLng(polyine.lat, polyine.lng));
            }
            map.addPolyline(polylineOptions);
        }
    }

    public PolylineOptions stylePolyLine(String type, int color) {
        PolylineOptions polylineOptions = new PolylineOptions().width(20);
        if (type.equals(ClassConstants.WALKING)) {
            polylineOptions.pattern(ClassConstants.WALK_PATTERN);
        }
        if (color != 0) {
            polylineOptions.color(color);
        } else {
            polylineOptions.color(Color.rgb(147, 35, 57));
        }
        return polylineOptions;
    }

}