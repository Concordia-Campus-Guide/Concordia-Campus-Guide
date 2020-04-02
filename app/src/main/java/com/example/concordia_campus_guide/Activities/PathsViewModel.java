package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

public class PathsViewModel extends ViewModel {

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }

    public Place getEntrance(Place place) {
        if (place instanceof RoomModel) {
            String campusCode = ((RoomModel) place).getCampus();
            String floorCode = ((RoomModel) place).getFloorCode();
            String buildingCode = floorCode.substring(0, floorCode.indexOf('-')).toUpperCase();
            //For indoor direction, no need to return the actual room from the db as the room coordinates will not be used by the PathFinder
            return new RoomModel(place.getCenterCoordinates(), "entrance", buildingCode + "-1", campusCode);
        }
        return place;
    }

    public boolean arePlacesSeparatedByATunnel(Place from, Place to) {
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String fromBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            floorCode = ((RoomModel) to).getFloorCode();
            String toBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            return fromBuilding.equalsIgnoreCase("H") && toBuilding.equalsIgnoreCase("MB") ||
                    fromBuilding.equalsIgnoreCase("MB") && toBuilding.equalsIgnoreCase("H");
        }
        return false;
    }

    public boolean areInSameBuilding(Place from, Place to) {
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String fromBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            floorCode = ((RoomModel) to).getFloorCode();
            String toBuilding = floorCode.toUpperCase().substring(0, floorCode.indexOf('-'));

            return fromBuilding.equalsIgnoreCase(toBuilding);
        }
        return false;
    }
}
