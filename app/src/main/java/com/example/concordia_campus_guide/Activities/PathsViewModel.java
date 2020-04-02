package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.example.concordia_campus_guide.Models.WalkingPoint;

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
            String buildingCode = floorCode.substring(0, floorCode.indexOf("-")).toUpperCase();
            //For indoor direction, no need to return the actual room from the db as the room coordinates will not be used by the PathFinder
            return new RoomModel(place.getCenterCoordinates(), "entrance", buildingCode + "-1", campusCode);
        }
        return place;
    }

    public boolean arePlacesSeparatedByATunnel(Place from, Place to) {
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String from_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            floorCode = ((RoomModel) to).getFloorCode();
            String to_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            return from_building.equalsIgnoreCase("H") && to_building.equalsIgnoreCase("MB") ||
                    from_building.equalsIgnoreCase("MB") && to_building.equalsIgnoreCase("H");
        }
        return false;
    }

    public boolean areInSameBuilding(Place from, Place to) {
        if (from instanceof RoomModel && to instanceof RoomModel) {
            String floorCode = ((RoomModel) from).getFloorCode();
            String from_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            floorCode = ((RoomModel) to).getFloorCode();
            String to_building = floorCode.toUpperCase().substring(0, floorCode.indexOf("-"));

            return from_building.equalsIgnoreCase(to_building);
        }
        return false;
    }
}
