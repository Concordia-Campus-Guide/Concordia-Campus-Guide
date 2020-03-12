package com.example.concordia_campus_guide.Activities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Floor;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;

public class RoutesActivityViewModel extends AndroidViewModel {

    AppDatabase appDB;
    Place from;
    Place to;

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
}
