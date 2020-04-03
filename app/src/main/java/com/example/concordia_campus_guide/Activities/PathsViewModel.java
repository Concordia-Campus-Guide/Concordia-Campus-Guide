package com.example.concordia_campus_guide.Activities;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Global.SelectingToFromState;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

public class PathsViewModel extends ViewModel {
    private AppDatabase appDB;

    public PathsViewModel(AppDatabase appDB) {
        this.appDB = appDB;
    }

    public Place getTo() {
        return SelectingToFromState.getTo();
    }

    public Place getFrom() {
        return SelectingToFromState.getFrom();
    }

    public Place getEntrance(Place place) {
        if (place instanceof RoomModel) {
            String floorCode = ((RoomModel) place).getFloorCode();
            String buildingCode = floorCode.substring(0, floorCode.indexOf('-')).toUpperCase();
            return appDB.roomDao().getRoomByIdAndFloorCode("entrance", buildingCode + "-1");
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
