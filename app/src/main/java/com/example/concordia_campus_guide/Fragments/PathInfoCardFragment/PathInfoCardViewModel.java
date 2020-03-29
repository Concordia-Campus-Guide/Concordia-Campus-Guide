package com.example.concordia_campus_guide.Fragments.PathInfoCardFragment;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Adapters.DirectionWrapper;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Models.Direction;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PathInfoCardViewModel extends ViewModel {
    private double totalDuration;

    public PathInfoCardViewModel(){}

    public double getTotalDuration(){
        return this.totalDuration;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double angle =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double center = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        return earthRadius * center; // Distance in km
    }

    public List<Direction> createOutdoorDirectionsList(List<DirectionWrapper> directionsResults){
        List<Direction> directionList = new ArrayList<>();
        totalDuration = 0;
        for (DirectionWrapper direction : directionsResults) {
            double minute = direction.getDirection().getDuration() / 60;
            direction.getDirection().setDuration(minute);

            totalDuration += direction.getDirection().getDuration();
            directionList.add(direction.getDirection());
        }
        return directionList;
    }

    public Direction createIndoorDirection(LatLng startLatLng, LatLng endLatLng, String description, double distance, double minutes){
        Direction direction = new Direction(startLatLng, endLatLng, ClassConstants.WALKING, description, distance);
        direction.setDuration(minutes);
        return direction;
    }

}
