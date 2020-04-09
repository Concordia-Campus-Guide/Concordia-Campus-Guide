package com.example.concordia_campus_guide.Helper;

import android.graphics.Color;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
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
            List<com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.LatLng> polyline = directionWrapper.getPolyline().decodePath();

            for (int i = 0; i < polyline.size(); i++) {
                polylineOptions.add(new LatLng(polyline.get(i).lat, polyline.get(i).lng));
            }
            map.addPolyline(polylineOptions);
        }
    }

    private PolylineOptions stylePolyLine(String type, int color) {
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
