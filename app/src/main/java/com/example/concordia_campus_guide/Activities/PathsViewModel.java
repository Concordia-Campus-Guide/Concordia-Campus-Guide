package com.example.concordia_campus_guide.Activities;

import android.content.Context;
import android.graphics.Color;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Helper.PathFinder;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.WalkingPoint;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathsViewModel extends ViewModel {

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }

    public Place getEntrance(Place place){
        if (place instanceof RoomModel) {
            String floorCode = ((RoomModel) place).getFloorCode();
            String buildingCode = floorCode.substring(0, floorCode.indexOf("-")).toUpperCase();
            //For indoor direction, no need to return the actual room from the db as the room coordinates will not be used by the PathFinder
            return new RoomModel(place.getCenterCoordinates(), "entrance", buildingCode + "-1");
        }
        return place;
    }

    public boolean arePlacesSeparatedByATunnel(Place from, Place to){
        if(from instanceof RoomModel && to instanceof RoomModel){
            String floorCode = ((RoomModel) from).getFloorCode();
            String from_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            floorCode = ((RoomModel) to).getFloorCode();
            String to_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            return from_building.equalsIgnoreCase("H") && to_building.equalsIgnoreCase("MB");
        }
        return false;
    }
}
