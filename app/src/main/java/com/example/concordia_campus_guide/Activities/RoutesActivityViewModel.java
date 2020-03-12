package com.example.concordia_campus_guide.Activities;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.MyCurrentPlace;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

public class RoutesActivityViewModel extends AndroidViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;

    private Location myCurrentLocation;

    public RoutesActivityViewModel(@NonNull Application application) {
        super(application);

        appDB = AppDatabase.getInstance(application.getApplicationContext());
    }

    public void setToUsingPlaceId(long placeId, String classExtendsPlaceType){
        if(classExtendsPlaceType.equalsIgnoreCase(RoomModel.class.getSimpleName())){
            to = appDB.roomDao().getRoomById(placeId);
        }
        else if(classExtendsPlaceType.equalsIgnoreCase(Floor.class.getSimpleName())){
            to = appDB.floorDao().getFloorById(placeId);
        }
        else if(classExtendsPlaceType.equalsIgnoreCase(Building.class.getSimpleName())){
            to = appDB.buildingDao().getBuildingById(placeId);
        }
    }

    public Place getTo(){
        return to;
    }

    public Place getFrom(){
        return from;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public Location getMyCurrentLocation() {
        return myCurrentLocation;
    }

    public void setMyCurrentLocation(Location myCurrentLocation) {
        if(myCurrentLocation!= null){
            this.myCurrentLocation = myCurrentLocation;
            this.from = new MyCurrentPlace(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
        }
        else{
            this.from = new MyCurrentPlace();
        }
    }
}
