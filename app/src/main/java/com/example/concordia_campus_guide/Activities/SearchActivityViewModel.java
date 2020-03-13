package com.example.concordia_campus_guide.Activities;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivityViewModel extends AndroidViewModel {

    List<Building> buildings;
    List<RoomModel> rooms;
    List<Floor> floors;

    Long fromId;
    Long toId;
    Location myCurrentLocation;
    String selectingToOrFrom;

    AppDatabase appDB;

    public SearchActivityViewModel(@NonNull Application application) {
        super(application);

        appDB = AppDatabase.getInstance(application.getApplicationContext());
        buildings = appDB.buildingDao().getAll();
        floors = appDB.floorDao().getAll();
        rooms = appDB.roomDao().getAll();
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Floor> getFloors(){
        return floors;
    }

    public List<RoomModel> getRooms(){
        return rooms;
    }

    public List<Place> getAllPlaces(){
        List<Place> places = new ArrayList<Place>();
        places.addAll(buildings);
        places.addAll(floors);
        places.addAll(rooms);
        return places;
    }

    public Location getMyCurrentLocation() {
        return myCurrentLocation;
    }

    public void setMyCurrentLocation(Location myCurrentLocation) {
        this.myCurrentLocation = myCurrentLocation;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getSelectingToOrFrom() {
        return selectingToOrFrom;
    }

    public void setSelectingToOrFrom(String selectingToOrFrom) {
        this.selectingToOrFrom = selectingToOrFrom;
    }
}